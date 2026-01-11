package com.example.domain.usecase.expense

import com.example.domain.entity.Expense
import com.example.domain.repository.ExpenseRepository

class GetExpenseUsecase(private val expenseRepository: ExpenseRepository) {
    suspend operator fun invoke(userId: String): List<Expense> {
        return expenseRepository.getExpensesByUser(userId)
    }

    suspend operator fun invoke(userId: String, budgetId: String): List<Expense> {
        return expenseRepository.getExpensesByBudget(userId, budgetId)
    }
}