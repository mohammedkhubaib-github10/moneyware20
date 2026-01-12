package com.example.domain.usecase.budget

import com.example.domain.entity.Budget
import com.example.domain.repository.BudgetRepository

class UpdateBudgetUsecase(
    private val budgetRepository: BudgetRepository,
    private val validateBudgetUsecase: ValidateBudgetUsecase
) {
    suspend operator fun invoke(userId: String, budget: Budget): BudgetResult {
        return when (val validation = validateBudgetUsecase(userId, budget)) {
            BudgetValidationResult.Valid -> {
                budgetRepository.updateBudget(userId, budget)
                BudgetResult.Success(budget)
            }

            is BudgetValidationResult.Error -> {
                BudgetResult.Error(validation)
            }
        }
    }
}