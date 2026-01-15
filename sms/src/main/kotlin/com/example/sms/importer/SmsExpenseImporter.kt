package com.example.sms.importer

import com.example.domain.usecase.budget.GetOrCreateMonthlyBudgetUsecase
import com.example.domain.usecase.expense.CreateExpenseUsecase
import com.example.sms.helper.toExpense
import com.example.sms.helper.toLocalDate
import com.example.sms.parser.GenericDebitSmsParser

class SmsExpenseImporter(
    private val parser: GenericDebitSmsParser,
    private val createExpenseUsecase: CreateExpenseUsecase,
    private val processedStore: ProcessedTransactionStore,
    private val getOrCreateCurrentMonthBudget: GetOrCreateMonthlyBudgetUsecase,
    private val userIdProvider: () -> String?
) {

    suspend fun import(
        body: String,
        timestamp: Long
    ) {
        //  Parse SMS
        val parsed = parser.parse(body, timestamp) ?: return

        //  Create dedup key
        val key = TransactionKeyFactory.from(
            parsed.amount,
            parsed.merchant,
            parsed.timestamp
        )

        //  Dedup check
        if (processedStore.isProcessed(key)) return

        //  Get user
        val userId = userIdProvider() ?: return
        val date = parsed.toLocalDate()
        //  Resolve current month budget
        val budget =
            getOrCreateCurrentMonthBudget(userId, date, 10000.0)

        // Map to Expense
        val expense =
            parsed.toExpense(
                userId = userId,
                budgetId = budget.budgetId
            )

        // Create expense
        createExpenseUsecase(expense)

        // Mark processed
        processedStore.markProcessed(key)
    }
}
