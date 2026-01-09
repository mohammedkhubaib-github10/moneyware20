package com.example.presentation.ui_state

import com.example.presentation.BudgetDialogMode

data class BudgetUIState(
    val budgetId: String = "",
    val budgetName: String = "",
    val budgetAmount: String = "",
    val isLoading: Boolean = true,
    val dialogMode: BudgetDialogMode = BudgetDialogMode.ADD,
    val dialogState: Boolean = false,
    val buttonState: Boolean = true,
    val error: String? = null
)