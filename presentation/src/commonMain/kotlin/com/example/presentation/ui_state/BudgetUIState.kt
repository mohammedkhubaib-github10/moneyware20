package com.example.presentation.ui_state

import com.example.presentation.DialogMode

data class BudgetUIState(
    val budgetId: String = "",
    val budgetName: String = "",
    val budgetAmount: String = "",
    val isLoading: Boolean = true,
    val dialogMode: DialogMode = DialogMode.ADD,
    val dialogState: Boolean = false,
    val buttonState: Boolean = true,
    val error: String? = null
)