package com.example.data.repository

import com.example.data.data_source.BudgetRemoteDataSource
import com.example.data.mapper.toDomain
import com.example.data.mapper.toDto
import com.example.domain.entity.Budget
import com.example.domain.repository.BudgetRepository

class BudgetRepositoryImpl(private val budgetRemoteDataSource: BudgetRemoteDataSource) :
    BudgetRepository {
    override suspend fun createBudget(budget: Budget): Budget {
        val budgetDto = budget.toDto()
        val id = budgetRemoteDataSource.createBudget(budgetDto)
        return budget.copy(budgetId = id)
    }

    override suspend fun updateBudget(budget: Budget) {
        val budgetDto = budget.toDto()
        budgetRemoteDataSource.updateBudget(budget.budgetId, budgetDto)
    }

    override suspend fun getBudgetById(budgetId: String): Budget? {
        val budgetDto = budgetRemoteDataSource.getBudgetById(budgetId)
        val budget = budgetDto?.toDomain(budgetId)
        return budget
    }

    override suspend fun getBudgets(): List<Budget> {
        val list = budgetRemoteDataSource.getBudgets()
        val newList = list.map { (id, dto) -> dto.toDomain(id) }
        return newList
    }

    override suspend fun isBudgetNameExists(name: String): Boolean {
        return budgetRemoteDataSource.isBudgetNameExists(name)
    }

    override suspend fun deleteBudget(budgetId: String) {
        budgetRemoteDataSource.deleteBudget(budgetId)
    }
}