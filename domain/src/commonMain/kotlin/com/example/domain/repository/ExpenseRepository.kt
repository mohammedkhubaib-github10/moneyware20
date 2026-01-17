package com.example.domain.repository

import com.example.domain.entity.Expense

interface ExpenseRepository {

    suspend fun addExpense(expense: Expense): Expense

    suspend fun updateExpense(expense: Expense)

    suspend fun getExpensesByUser(userId: String): List<Expense>

    suspend fun getExpensesByBudget(userId: String, budgetId: String): List<Expense>

    suspend fun deleteExpense(expenseId: String)
    suspend fun createProcessedExpense(
        userId: String,
        hash: String,
        smsTimeStamp: Long,
        expense: Expense
    ): Boolean
}
