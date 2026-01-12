package com.example.domain.usecase.budget

import com.example.domain.entity.Budget
import com.example.domain.repository.BudgetRepository

class GetBudgetUsecase(private val budgetRepository: BudgetRepository) {
    suspend operator fun invoke(userId: String): List<Budget> {
        return budgetRepository.getBudgets(userId = userId)
    }

    suspend operator fun invoke(userId: String, budgetId: String): Budget? {
        return budgetRepository.getBudgetById(userId, budgetId)
    }

}