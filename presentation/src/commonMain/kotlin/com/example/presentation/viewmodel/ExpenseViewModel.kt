package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.presentation.DialogMode
import com.example.presentation.ui_state.ExpenseUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ExpenseViewModel : ViewModel() {
    private val _expenseUIState = MutableStateFlow(ExpenseUIState())
    val expenseUIState = _expenseUIState.asStateFlow()

    fun setButton(boolean: Boolean) {
        _expenseUIState.value = _expenseUIState.value.copy(buttonState = boolean)
    }

    fun setDialog(boolean: Boolean) {
        _expenseUIState.value = _expenseUIState.value.copy(dialogState = boolean)
    }

    fun setExpenseDialogMode(mode: DialogMode) {
        _expenseUIState.value = _expenseUIState.value.copy(dialogMode = mode)
    }

    fun setDatePicker(boolean: Boolean) {
        _expenseUIState.value = _expenseUIState.value.copy(datePickerState = boolean)
    }

    fun onExpenseIdChange(expenseId: String) {
        _expenseUIState.value = _expenseUIState.value.copy(
            expenseId = expenseId
        )
    }

    // Inputs
    fun onExpenseNameChange(name: String) {
        _expenseUIState.value = _expenseUIState.value.copy(
            expenseName = name
        )
    }

    fun onExpenseAmountChange(amount: String) {

        _expenseUIState.value = _expenseUIState.value.copy(
            expenseAmount = amount
        )
    }

    fun onCancel() {
        _expenseUIState.value = _expenseUIState.value.copy(expenseName = "", expenseAmount = "")
    }

}