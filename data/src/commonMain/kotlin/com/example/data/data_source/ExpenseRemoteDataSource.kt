package com.example.data.data_source

import com.example.data.dto.ExpenseDto

interface ExpenseRemoteDataSource {
    suspend fun addExpense(dto: ExpenseDto): String
    suspend fun updateExpense(expenseId: String, dto: ExpenseDto)
    suspend fun getExpensesByUser(
        userId: String
    ): List<Pair<String, ExpenseDto>>

    suspend fun getExpensesByBudget(
        userId: String,
        budgetId: String
    ): List<Pair<String, ExpenseDto>>

    suspend fun deleteExpense(expenseId: String)
    suspend fun createProcessedExpense(userId: String, hash: String, smsTimeStamp: Long, dto: ExpenseDto): Boolean
}