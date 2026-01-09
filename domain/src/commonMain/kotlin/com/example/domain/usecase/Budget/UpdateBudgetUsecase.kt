package com.example.domain.usecase.Budget

import com.example.domain.entity.Budget
import com.example.domain.repository.BudgetRepository

class UpdateBudgetUsecase(private val budgetRepository: BudgetRepository) {
    suspend operator fun invoke(userId: String, budget: Budget) {
        budgetRepository.updateBudget(userId = userId, budget)
    }
}