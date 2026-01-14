package com.example.domain.usecase.expense

import com.example.domain.entity.Expense
import com.example.domain.notification.BudgetThresholdNotifier
import com.example.domain.repository.ExpenseRepository

class CreateExpenseUsecase(
    private val expenseRepository: ExpenseRepository,
    private val notifier: BudgetThresholdNotifier
) {
    suspend operator fun invoke(expense: Expense): String {
        val saved = expenseRepository.addExpense(expense)
        notifier.onExpenseAdded(expense.userId, expense.budgetId)
        return saved.expenseId
    }
}