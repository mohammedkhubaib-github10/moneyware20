package com.example.domain.usecase.Budget

import com.example.domain.entity.Budget
import com.example.domain.repository.BudgetRepository

class GetBudgetUsecase(private val budgetRepository: BudgetRepository) {
    suspend operator fun invoke(): List<Budget> {
        return budgetRepository.getBudgets()
    }

}