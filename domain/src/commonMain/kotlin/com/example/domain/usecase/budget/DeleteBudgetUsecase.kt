package com.example.domain.usecase.budget

import com.example.domain.repository.BudgetRepository
import com.example.domain.repository.ExpenseRepository

class DeleteBudgetUsecase(
    private val budgetRepository: BudgetRepository,
    private val expenseRepository: ExpenseRepository
) {
    suspend operator fun invoke(userId: String, budgetId: String) {
        val expenses =
            expenseRepository.getExpensesByBudget(userId, budgetId)

        // 2️⃣ Delete all expenses
        expenses.forEach {
            expenseRepository.deleteExpense(it.expenseId)
        }
        budgetRepository.deleteBudget(userId = userId, budgetId = budgetId)
    }
}