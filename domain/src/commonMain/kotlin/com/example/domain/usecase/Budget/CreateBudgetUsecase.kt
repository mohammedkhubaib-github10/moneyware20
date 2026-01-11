package com.example.domain.usecase.Budget

import com.example.domain.entity.Budget
import com.example.domain.repository.BudgetRepository

class CreateBudgetUsecase(
    private val budgetRepository: BudgetRepository,
    private val validateBudgetUsecase: ValidateBudgetUsecase
) {
    suspend operator fun invoke(userId: String, budget: Budget): BudgetResult {

        return when (val validation = validateBudgetUsecase(userId, budget)) {
            BudgetValidationResult.Valid -> {
                val created = budgetRepository.createBudget(userId, budget)
                BudgetResult.Success(created)
            }

            is BudgetValidationResult.Error -> {
                BudgetResult.Error(validation)
            }
        }
    }
}


