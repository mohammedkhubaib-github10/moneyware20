package com.example.domain.usecase.Budget

import com.example.domain.entity.Budget
import com.example.domain.repository.BudgetRepository

class CreateBudgetUsecase(
    private val budgetRepository: BudgetRepository,
    private val validateBudgetUsecase: ValidateBudgetUsecase
) {
    suspend operator fun invoke(budget: Budget): CreateBudgetResult {

        return when (val validation = validateBudgetUsecase(budget)) {
            BudgetValidationResult.Valid -> {
                val created = budgetRepository.createBudget(budget)
                CreateBudgetResult.Success(created)
            }
            is BudgetValidationResult.Error -> {
                CreateBudgetResult.Error(validation)
            }
        }
    }
}


sealed interface CreateBudgetResult {
    data class Success(val budget: Budget) : CreateBudgetResult
    data class Error(val error: BudgetValidationResult.Error) : CreateBudgetResult
}
