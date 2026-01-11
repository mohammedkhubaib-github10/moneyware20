package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Budget
import com.example.domain.usecase.Budget.CreateBudgetResult
import com.example.domain.usecase.Budget.CreateBudgetUsecase
import com.example.domain.usecase.Budget.DeleteBudgetUsecase
import com.example.domain.usecase.Budget.GetBudgetUsecase
import com.example.domain.usecase.Budget.UpdateBudgetUsecase
import com.example.domain.usecase.SignOutUsecase
import com.example.presentation.AuthState
import com.example.presentation.DialogMode
import com.example.presentation.mapper.toUIModel
import com.example.presentation.mapper.toUiMessage
import com.example.presentation.ui_model.BudgetUIModel
import com.example.presentation.ui_state.BudgetUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BudgetViewModel(
    private val createBudgetUsecase: CreateBudgetUsecase,
    private val getBudgetUsecase: GetBudgetUsecase,
    private val updateBudgetUsecase: UpdateBudgetUsecase,
    private val deleteBudgetUsecase: DeleteBudgetUsecase,
    private val signOutUsecase: SignOutUsecase,
    private val authState: AuthState
) : ViewModel() {

    private val _budgetUIState = MutableStateFlow(BudgetUIState())
    val budgetUIState = _budgetUIState.asStateFlow()

    private val _budgetList = MutableStateFlow<List<BudgetUIModel>>(emptyList())
    val budgetList = _budgetList.asStateFlow()


    /* ---------------- UI Events ---------------- */
    fun setButton(boolean: Boolean) {
        _budgetUIState.value = _budgetUIState.value.copy(buttonState = boolean)
    }

    fun setDialog(boolean: Boolean) {
        _budgetUIState.value = _budgetUIState.value.copy(dialogState = boolean)
    }

    fun setBudgetDialogMode(mode: DialogMode) {
        _budgetUIState.value = _budgetUIState.value.copy(dialogMode = mode)
    }

    fun onBudgetIdChange(budgetId: String) {
        _budgetUIState.value = _budgetUIState.value.copy(
            budgetId = budgetId
        )
    }

    // Inputs
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


    fun onError(message: String) {
        _budgetUIState.value = _budgetUIState.value.copy(error = message)
    }

    //actions
    fun onAddBudget() {
        val userId = authState.user.value?.userId ?: run {
            onError("User not logged in")
            return
        }
        viewModelScope.launch {

            val uiState = _budgetUIState.value

            val budget = Budget(
                budgetId = uiState.budgetId,
                budgetName = uiState.budgetName,
                budgetAmount = uiState.budgetAmount.toDouble()
            )

            when (val result = createBudgetUsecase(userId, budget)) {

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
        val userId = authState.user.value?.userId ?: return
        viewModelScope.launch {
            val list = getBudgetUsecase(userId)
            _budgetList.value = list.map {
                it.toUIModel()
            }
            _budgetUIState.value = _budgetUIState.value.copy(isLoading = false)

        }
    }

    fun onEditBudget() {
        val userId = authState.user.value?.userId ?: return
        viewModelScope.launch {
            val uiState = _budgetUIState.value

            val budget = Budget(
                budgetId = uiState.budgetId,
                budgetName = uiState.budgetName,
                budgetAmount = uiState.budgetAmount.toDouble()
            )
            updateBudgetUsecase(userId, budget)
            _budgetUIState.value = BudgetUIState()
            getBudgets()
        }
    }

    fun deleteBudget(budgetId: String) {
        val userId = authState.user.value?.userId ?: return
        viewModelScope.launch {
            deleteBudgetUsecase(userId = userId, budgetId = budgetId)
            getBudgets()

        }
    }

    fun onCancel() {
        _budgetUIState.value = _budgetUIState.value.copy(budgetName = "", budgetAmount = "")
    }

    fun signOut() {
        authState.onLogout()
        _budgetUIState.value = BudgetUIState()
        signOutUsecase()
    }
}

