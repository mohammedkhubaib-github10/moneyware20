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
    private val _budgetList = MutableStateFlow<List<BudgetUIModel>>(emptyList())
    val budgetList = _budgetList.asStateFlow()

    init {
        getBudgets()
    }

    fun setButton(boolean: Boolean) {
        _budgetUIState.value = _budgetUIState.value.copy(buttonState = boolean)
    }

    /* ---------------- UI Events ---------------- */
    fun setDialog(boolean: Boolean) {
        _budgetUIState.value = _budgetUIState.value.copy(dialogState = boolean)
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
                    setDialog(false)
                    setButton(true)
                    getBudgets()
                }

                is CreateBudgetResult.Error -> {
                    val message = result.error.toUiMessage()
                    onError(message)
                    setButton(true)
                }
            }
        }
    }

    fun getBudgets() {
        viewModelScope.launch {
            val list = getBudgetUsecase("khubaib")
            _budgetList.value = list.map {
                it.toUIModel()
            }
            _budgetUIState.value = _budgetUIState.value.copy(isLoading = false)

        }
    }

    fun onCancel() {
        _budgetUIState.value = BudgetUIState()
    }
}

