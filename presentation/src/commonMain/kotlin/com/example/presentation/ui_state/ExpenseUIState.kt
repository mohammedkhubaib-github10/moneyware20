package com.example.presentation.ui_state

import com.example.domain.utility.today
import com.example.presentation.DialogMode
import kotlinx.datetime.LocalDate

data class ExpenseUIState(
    val expenseId: String = "",
    val expenseName: String = "",
    val date: LocalDate = today,
    val expenseAmount: String = "",
    val dialogState: Boolean = false,
    val dialogMode: DialogMode = DialogMode.ADD,
    val isLoading: Boolean = false,
    val buttonState: Boolean = true,
    val datePickerState: Boolean = false
)
