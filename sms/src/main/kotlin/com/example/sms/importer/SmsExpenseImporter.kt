package com.example.sms.importer

import android.util.Log
import com.example.domain.usecase.expense.CreateExpenseUsecase
import com.example.sms.helper.toExpense
import com.example.sms.parser.ParsedTransaction

class SmsExpenseImporter(
    private val createExpenseUsecase: CreateExpenseUsecase,
    private val processedStore: ProcessedTransactionStore
) {

    suspend fun import(
        parsed: ParsedTransaction?,
        userId: String,
        budgetId: String
    ) {
        //  Parse SMS
        if (parsed == null) return
        //  Create dedup key
        val key = TransactionKeyFactory.from(
            parsed.amount,
            parsed.merchant,
            parsed.timestamp
        )
        Log.d("trans", parsed.toString())
        //  Dedup check
        if (processedStore.isProcessed(key)) return

        // Map to Expense
        val expense =
            parsed.toExpense(
                userId = userId,
                budgetId = budgetId
            )

        // Create expense
        createExpenseUsecase(expense)

        // Mark processed
        processedStore.markProcessed(key)
    }
}
