package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Budget
import com.example.domain.usecase.Budget.CreateBudgetResult
import com.example.domain.usecase.Budget.CreateBudgetUsecase
import com.example.presentation.mapper.toUiMessage
import com.example.presentation.ui_event.UIEvent
import com.example.presentation.ui_state.BudgetType
import com.example.presentation.ui_state.BudgetUIState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BudgetViewModel(private val createBudgetUsecase: CreateBudgetUsecase) : ViewModel() {
    private val _uiEvent = MutableSharedFlow<UIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()
    private val _budgetUIState = MutableStateFlow(BudgetUIState())
    val budgetUIState: StateFlow<BudgetUIState> = _budgetUIState.asStateFlow()
    private val _dialogState = MutableStateFlow(false)
    val dialogState: StateFlow<Boolean> = _dialogState

    /* ---------------- UI Events ---------------- */
    fun toggleDialog(dialog: Boolean) {
        _dialogState.value = dialog
    }

    fun onBudgetNameChange(name: String) {
        _budgetUIState.value = _budgetUIState.value.copy(
            budgetName = name
        )
    }

    fun onBudgetAmountChange(amount: String) {
        _budgetUIState.value = _budgetUIState.value.copy(
            budgetAmount = amount
        )
    }

    fun onBudgetTypeChange(type: BudgetType) {
        _budgetUIState.value = _budgetUIState.value.copy(
            budgetType = type
        )
    }

    fun onAddBudget() {
        viewModelScope.launch {

            val uiState = _budgetUIState.value

            val budget = Budget(
                budgetId = "",
                budgetName = uiState.budgetName,
                budgetAmount = uiState.budgetAmount.toDouble()
            )

            when (val result = createBudgetUsecase(budget)) {

                is CreateBudgetResult.Success -> {
                    _budgetUIState.value = BudgetUIState()
                    toggleDialog(false)
                }

                is CreateBudgetResult.Error -> {
                    val message = result.error.toUiMessage()
                    _uiEvent.emit(UIEvent.ShowSnackbar(message))
                }
            }
        }
    }


    fun onCancel() {
        _budgetUIState.value = BudgetUIState()
    }
}

