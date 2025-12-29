package com.example.domain.repository

import com.example.domain.entity.Expense

interface ExpenseRepository {

    suspend fun addExpense(expense: Expense)

    suspend fun updateExpense(expense: Expense)

    suspend fun getExpensesByBudget(budgetId: String): List<Expense>

    suspend fun deleteExpense(expenseId: String)
}
