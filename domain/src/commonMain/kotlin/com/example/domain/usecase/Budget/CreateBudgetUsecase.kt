package com.example.domain.usecase.Budget

import com.example.domain.entity.Budget
import com.example.domain.repository.BudgetRepository

class CreateBudgetUsecase(private val budgetRepository: BudgetRepository) {
    suspend operator fun invoke(budget: Budget) {

    }
}