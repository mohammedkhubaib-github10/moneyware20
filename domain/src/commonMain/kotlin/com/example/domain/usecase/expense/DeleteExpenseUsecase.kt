package com.example.domain.usecase.expense

import com.example.domain.repository.ExpenseRepository

class DeleteExpenseUsecase(private val expenseRepository: ExpenseRepository) {
    suspend operator fun invoke(expenseId: String) {
        expenseRepository.deleteExpense(expenseId)
    }
}