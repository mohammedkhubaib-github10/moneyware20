package com.example.domain.usecase.Budget

import com.example.domain.repository.BudgetRepository

class DeleteBudgetUsecase(private val budgetRepository: BudgetRepository) {
    suspend operator fun invoke(userId: String, budgetId: String) {
        budgetRepository.deleteBudget(userId = userId, budgetId = budgetId)
    }
}