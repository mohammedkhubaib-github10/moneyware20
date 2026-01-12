package com.example.presentation.mapper

import com.example.domain.entity.Budget
import com.example.domain.usecase_model.BudgetSummary
import com.example.presentation.ui_model.BudgetUIModel

fun Budget.toUIModel(): BudgetUIModel = BudgetUIModel(
    budgetId = budgetId,
    budgetName = budgetName,
    budgetAmount = budgetAmount.toString()
)

fun BudgetSummary.toBudgetCard(
): BudgetUIModel {

    return BudgetUIModel(
        budgetId = budgetId,
        budgetName = budgetName,
        budgetAmount = budgetAmount.toString(),
        totalExpense = totalExpense.toString(),
        balance = balance.toString(),
        percentageUsed = percentageUsed.toString()
    )
}
