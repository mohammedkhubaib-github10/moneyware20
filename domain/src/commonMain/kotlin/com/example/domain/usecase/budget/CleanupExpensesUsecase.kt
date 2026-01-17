package com.example.domain.usecase.budget

import com.example.domain.repository.ExpenseRepository

class CleanupExpensesUsecase(private val expenseRepository: ExpenseRepository) {
    suspend operator fun invoke(userId: String, budgetId: String) {
        val expenses =
            expenseRepository.getExpensesByBudget(userId, budgetId)
        expenses.forEach {
            expenseRepository.deleteExpense(it.expenseId)
        }
    }
}