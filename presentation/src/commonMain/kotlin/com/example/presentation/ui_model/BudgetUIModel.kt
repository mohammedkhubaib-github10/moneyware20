package com.example.presentation.ui_model

import kotlinx.serialization.Serializable

@Serializable
data class BudgetUIModel(
    val budgetId: String,
    val budgetName: String,
    val budgetAmount: String,
    val totalExpense: String = "0",
    val balance: String = "0",
    val percentageUsed: String = "0"
)
