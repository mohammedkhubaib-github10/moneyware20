package com.example.presentation.ui_model

import kotlinx.serialization.Serializable

@Serializable
data class BudgetUIModel(
    val budgetId: String,
    val budgetName: String,
    val budgetAmount: String
)
