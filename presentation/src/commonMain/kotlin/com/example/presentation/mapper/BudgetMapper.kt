package com.example.presentation.mapper

import com.example.domain.entity.Budget
import com.example.presentation.ui_model.BudgetUIModel

fun Budget.toUIModel(): BudgetUIModel = BudgetUIModel(
    budgetId = budgetId,
    budgetName = budgetName,
    budgetAmount = budgetAmount.toString()
)