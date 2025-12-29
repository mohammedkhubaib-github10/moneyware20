package com.example.domain.repository

import com.example.domain.entity.Budget

interface BudgetRepository {

    suspend fun createBudget(budget: Budget)

    suspend fun updateBudget(budget: Budget)

    suspend fun getBudgetById(budgetId: String): Budget?

    suspend fun getBudgets(): List<Budget>

    suspend fun isBudgetNameExists(name: String): Boolean

    suspend fun deleteBudget(budgetId: String)
}
