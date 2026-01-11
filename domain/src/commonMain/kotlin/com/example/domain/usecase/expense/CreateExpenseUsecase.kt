package com.example.domain.usecase.expense

import com.example.domain.entity.Expense
import com.example.domain.repository.ExpenseRepository

class CreateExpenseUsecase(private val expenseRepository: ExpenseRepository) {
    suspend operator fun invoke(expense: Expense) {
        expenseRepository.addExpense(expense)
    }
}