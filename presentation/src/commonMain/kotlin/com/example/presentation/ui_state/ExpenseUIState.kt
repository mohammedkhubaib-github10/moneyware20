package com.example.presentation.ui_state

import com.example.presentation.DialogMode

data class ExpenseUIState(
    val expenseId: String = "",
    val expenseName: String = "",
    val expenseAmount: String = "",
    val dialogState: Boolean = false,
    val dialogMode: DialogMode = DialogMode.ADD,
    val isLoading: Boolean = false,
    val buttonState: Boolean = true,
    val datePickerState: Boolean = false
)
