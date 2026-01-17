package com.example.domain.usecase.expense

import com.example.domain.entity.Expense
import com.example.domain.repository.ExpenseRepository

class CreateProcessedExpenseUsecase(private val expenseRepository: ExpenseRepository) {
    suspend operator fun invoke(userId: String, hash: String, smsTimeStamp: Long, expense: Expense): Boolean {
        return expenseRepository.createProcessedExpense(userId, hash, smsTimeStamp, expense)
    }
}