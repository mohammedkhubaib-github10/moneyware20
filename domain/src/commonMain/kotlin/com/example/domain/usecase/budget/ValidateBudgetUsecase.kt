package com.example.domain.usecase.budget

import com.example.domain.entity.Budget
import com.example.domain.repository.BudgetRepository

class ValidateBudgetUsecase(
    private val budgetRepository: BudgetRepository
) {
    suspend operator fun invoke(userId: String, budget: Budget): BudgetValidationResult {
        val exist = budgetRepository.isBudgetNameExists(userId = userId, budget.budgetName)
        if (exist.first && budget.budgetId != exist.second) {
            return BudgetValidationResult.Error.DuplicateName
        }
        return BudgetValidationResult.Valid
    }
}


sealed interface BudgetValidationResult {
    data object Valid : BudgetValidationResult

    sealed interface Error : BudgetValidationResult {
        data object InvalidAmount : Error
        data object DuplicateName : Error
    }
}

sealed interface BudgetResult {
    data class Success(val budget: Budget) : BudgetResult
    data class Error(val error: BudgetValidationResult.Error) : BudgetResult
}
