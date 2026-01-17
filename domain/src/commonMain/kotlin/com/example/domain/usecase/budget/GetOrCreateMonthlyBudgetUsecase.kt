package com.example.domain.usecase.budget

import com.example.domain.entity.Budget
import com.example.domain.repository.BudgetRepository
import com.example.domain.utility.currentMonth
import com.example.domain.utility.currentYear

class GetOrCreateMonthlyBudgetUsecase(
    private val budgetRepository: BudgetRepository
) {

    suspend operator fun invoke(
        userId: String,
        monthlyBudget: Double
    ): Budget {

        val budgetName =
            "${currentMonth.name.lowercase().replaceFirstChar { it.uppercase() }} $currentYear"

        // Check if budget exists
        val (exists, budgetId) =
            budgetRepository.isBudgetNameExists(userId, budgetName)

        return if (exists && budgetId != null) {
            budgetRepository.getBudgetById(userId, budgetId)
                ?: error("Budget not found after existence check")
        } else {
            val budget = Budget(
                budgetId = "",
                budgetName = budgetName,
                budgetAmount = monthlyBudget
            )
            budgetRepository.createBudget(userId, budget)
        }
    }
}
