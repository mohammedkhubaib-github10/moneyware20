package com.example.domain.usecase_model

data class BudgetSummary(
    val budgetId: String,
    val budgetName: String,
    val budgetAmount: Double,
    val totalExpense: Double,
    val balance: Double,
    val percentageUsed: Double
)
