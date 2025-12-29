package com.example.domain.usecase_models

import com.example.domain.entity.Budget

data class BudgetSummary(
    val budget: Budget,
    val totalExpense: Double,
    val balance: Double
)
