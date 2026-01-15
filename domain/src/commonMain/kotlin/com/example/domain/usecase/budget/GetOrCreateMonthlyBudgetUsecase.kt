package com.example.domain.usecase.budget

import com.example.domain.entity.Budget
import com.example.domain.repository.BudgetRepository
import kotlinx.datetime.LocalDate

class GetOrCreateMonthlyBudgetUsecase(
    private val budgetRepository: BudgetRepository
) {

    suspend operator fun invoke(
        userId: String,
        date: LocalDate,
        monthlyBudget: Double
    ): Budget {

        val budgetName = monthYearName(date)

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

    private fun monthYearName(date: LocalDate): String {
        val month = date.month.name.lowercase()
            .replaceFirstChar { it.uppercase() }
        return "$month ${date.year}"
    }

}
