package com.example.domain.usecase.expense

import com.example.domain.entity.Expense
import com.example.domain.notification.BudgetThresholdNotifier
import com.example.domain.repository.ExpenseRepository

class CreateProcessedExpenseUsecase(
    private val expenseRepository: ExpenseRepository,
    private val notifier: BudgetThresholdNotifier

) {
    suspend operator fun invoke(
        userId: String,
        hash: String,
        smsTimeStamp: Long,
        expense: Expense
    ): Boolean {
        val inserted = expenseRepository.createProcessedExpense(userId, hash, smsTimeStamp, expense)
        return if (inserted) {
            notifier.onExpenseAdded(expense.userId, expense.budgetId)
            true
        } else false
    }
}