package com.example.data.repository

import com.example.data.data_source.BudgetRemoteDataSource
import com.example.data.mapper.toDomain
import com.example.data.mapper.toDto
import com.example.domain.entity.Budget
import com.example.domain.repository.BudgetRepository

class BudgetRepositoryImpl(private val budgetRemoteDataSource: BudgetRemoteDataSource) :
    BudgetRepository {
    override suspend fun createBudget(userId: String, budget: Budget): Budget {
        val budgetDto = budget.toDto()
        val id = budgetRemoteDataSource.createBudget(userId, budgetDto)
        return budget.copy(budgetId = id)
    }

    override suspend fun updateBudget(userId: String, budget: Budget) {
        val budgetDto = budget.toDto()
        budgetRemoteDataSource.updateBudget(userId, budget.budgetId, budgetDto)
    }

    override suspend fun getBudgetById(userId: String, budgetId: String): Budget? {
        val budgetDto = budgetRemoteDataSource.getBudgetById(userId, budgetId)
        val budget = budgetDto?.toDomain(budgetId)
        return budget
    }

    override suspend fun getBudgets(userId: String): List<Budget> {
        val list = budgetRemoteDataSource.getBudgets(userId)
        val newList = list.map { (id, dto) -> dto.toDomain(id) }
        return newList
    }

    override suspend fun isBudgetNameExists(userId: String, name: String): Boolean {
        return budgetRemoteDataSource.isBudgetNameExists(userId, name)
    }

    override suspend fun deleteBudget(userId: String, budgetId: String) {
        budgetRemoteDataSource.deleteBudget(userId, budgetId)
    }
}