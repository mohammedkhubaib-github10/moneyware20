package com.example.presentation.ui_state

data class BudgetUIState(
    val budgetName: String = "",
    val budgetAmount: String = "",
    val isLoading: Boolean = true,
    val dialogState: Boolean = false,
    val buttonState: Boolean = true,
    val error: String? = null
)