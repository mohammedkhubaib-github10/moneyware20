package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Budget
import com.example.domain.usecase.Budget.CreateBudgetResult
import com.example.domain.usecase.Budget.CreateBudgetUsecase
import com.example.domain.usecase.Budget.GetBudgetUsecase
import com.example.presentation.mapper.toUIModel
import com.example.presentation.mapper.toUiMessage
import com.example.presentation.ui_model.BudgetUIModel
import com.example.presentation.ui_state.BudgetType
import com.example.presentation.ui_state.BudgetUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BudgetViewModel(
    private val createBudgetUsecase: CreateBudgetUsecase,
    private val getBudgetUsecase: GetBudgetUsecase
) : ViewModel() {

    private val _budgetUIState = MutableStateFlow(BudgetUIState())
    val budgetUIState = _budgetUIState.asStateFlow()

    private val _showLoading = MutableStateFlow(true)
    val showLoading = _showLoading.asStateFlow()
    private val _dialogState = MutableStateFlow(false)
    val dialogState = _dialogState.asStateFlow()

    private val _buttonState = MutableStateFlow(true)
    val buttonState = _buttonState.asStateFlow()
    private val _budgetList = MutableStateFlow<List<BudgetUIModel>>(emptyList())
    val budgetList = _budgetList.asStateFlow()
    fun toggleButton(boolean: Boolean) {
        _buttonState.value = boolean
    }

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

    fun onError(message: String) {
        _budgetUIState.value = _budgetUIState.value.copy(error = message)
    }

    fun onAddBudget() {
        viewModelScope.launch {

            val uiState = _budgetUIState.value

            val budget = Budget(
                budgetId = "",
                budgetName = uiState.budgetName,
                budgetAmount = uiState.budgetAmount.toDouble()
            )

            when (val result = createBudgetUsecase("khubaib", budget)) {

                is CreateBudgetResult.Success -> {
                    _budgetUIState.value = BudgetUIState()
                    toggleDialog(false)
                    toggleButton(true)
                    showBudgets()
                }

                is CreateBudgetResult.Error -> {
                    val message = result.error.toUiMessage()
                    onError(message)
                    toggleButton(true)
                }
            }
        }
    }

    fun showBudgets() {
        viewModelScope.launch {
            val list = getBudgetUsecase("khubaib")
            _budgetList.value = list.map {
                it.toUIModel()
            }
            _showLoading.value = false
        }
    }

    fun onCancel() {
        _budgetUIState.value = BudgetUIState()
    }
}

