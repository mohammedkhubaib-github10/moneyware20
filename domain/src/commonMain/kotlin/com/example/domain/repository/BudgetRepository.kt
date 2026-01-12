package com.example.domain.repository

import com.example.domain.entity.Budget

interface BudgetRepository {

    suspend fun createBudget(userId: String, budget: Budget): Budget

    suspend fun updateBudget(userId: String, budget: Budget)

    suspend fun getBudgetById(userId: String, budgetId: String): Budget?

    suspend fun getBudgets(userId: String): List<Budget>

    suspend fun isBudgetNameExists(userId: String, name: String): Pair<Boolean, String?>

    suspend fun deleteBudget(userId: String, budgetId: String)
}
