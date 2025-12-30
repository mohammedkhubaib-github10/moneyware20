package com.example.data.repository

import com.example.domain.entity.Budget
import com.example.domain.repository.BudgetRepository

class BudgetRepositoryImpl : BudgetRepository {
    override suspend fun createBudget(budget: Budget): Budget {
        return budget
    }

    override suspend fun updateBudget(budget: Budget) {
        TODO("Not yet implemented")
    }

    override suspend fun getBudgetById(budgetId: String): Budget? {
        TODO("Not yet implemented")
    }

    override suspend fun getBudgets(): List<Budget> {
        TODO("Not yet implemented")
    }

    override suspend fun isBudgetNameExists(name: String): Boolean {
        return name.trim().equals("khubaib")
    }

    override suspend fun deleteBudget(budgetId: String) {
        TODO("Not yet implemented")
    }
}