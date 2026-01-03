package com.example.presentation.ui_state

import com.example.presentation.ui_state.BudgetType

data class BudgetUIState(
    val budgetName: String = "",
    val budgetAmount: String = "",
    val budgetType: BudgetType = BudgetType.MANUAL,
    val isLoading: Boolean = true,
    val dialogState: Boolean = false,
    val buttonState: Boolean = true,
    val error: String? = null
)