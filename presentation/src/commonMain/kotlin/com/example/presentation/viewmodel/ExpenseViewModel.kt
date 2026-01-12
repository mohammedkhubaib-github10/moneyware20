package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Expense
import com.example.domain.usecase.expense.CreateExpenseUsecase
import com.example.domain.usecase.expense.DeleteExpenseUsecase
import com.example.domain.usecase.expense.GetExpenseUsecase
import com.example.domain.usecase.expense.UpdateExpenseUsecase
import com.example.presentation.AuthState
import com.example.presentation.DialogMode
import com.example.presentation.mapper.toUIModel
import com.example.presentation.ui_model.ExpenseUIModel
import com.example.presentation.ui_state.ExpenseUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class ExpenseViewModel(
    private val createExpenseUsecase: CreateExpenseUsecase,
    private val getExpenseUsecase: GetExpenseUsecase,
    private val updateExpenseUsecase: UpdateExpenseUsecase,
    private val deleteExpenseUsecase: DeleteExpenseUsecase,
    private val authState: AuthState

) : ViewModel() {
    private val _expenseUIState = MutableStateFlow(ExpenseUIState())
    val expenseUIState = _expenseUIState.asStateFlow()
    private val _expenseList = MutableStateFlow<List<ExpenseUIModel>>(emptyList())
    val expenseList = _expenseList.asStateFlow()

    init {
        observeAuth()
    }

    private fun observeAuth() {
        viewModelScope.launch {
            authState.user.collect { user ->
                if (user == null) {
                    clearState()
                }
            }
        }
    }

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

    fun onDateChange(date: LocalDate) {
        _expenseUIState.value = _expenseUIState.value.copy(
            date = date
        )
    }

    fun onCancel() {
        _expenseUIState.value = _expenseUIState.value.copy(expenseName = "", expenseAmount = "")
    }

    fun onAddExpense(budgetId: String) {
        val userId = authState.user.value?.userId ?: return

        viewModelScope.launch {

            val uiState = _expenseUIState.value
            val expense = Expense(
                expenseId = uiState.expenseId,
                expenseName = uiState.expenseName,
                expenseAmount = uiState.expenseAmount.toDouble(),
                date = uiState.date,
                userId = userId,
                budgetId = budgetId
            )
            createExpenseUsecase(expense)
            _expenseUIState.value = ExpenseUIState()
            refreshExpense(budgetId)

        }
    }

    fun getExpensesByBudget(budgetId: String) {
        val userId = authState.user.value?.userId ?: return
        viewModelScope.launch {
            val list = getExpenseUsecase(userId, budgetId)
            _expenseList.value = list.map {
                it.toUIModel()
            }
            _expenseUIState.value = _expenseUIState.value.copy(isLoading = false)
        }
    }

    fun onEditExpense(budgetId: String) {
        val userId = authState.user.value?.userId ?: return
        viewModelScope.launch {
            val uiState = _expenseUIState.value

            val expense = Expense(
                expenseId = uiState.expenseId,
                expenseName = uiState.expenseName,
                expenseAmount = uiState.expenseAmount.toDouble(),
                date = uiState.date,
                budgetId = budgetId,
                userId = userId
            )
            updateExpenseUsecase(expense)
            _expenseUIState.value = ExpenseUIState()
            refreshExpense(budgetId)
        }
    }

    fun onDeleteExpense(budgetId: String, expenseId: String) {
        viewModelScope.launch {
            deleteExpenseUsecase(expenseId)
            refreshExpense(budgetId)
        }
    }

    fun clearState() {
        _expenseList.value = emptyList()
        _expenseUIState.value = ExpenseUIState()
    }

    fun refreshExpense(budgetId: String) {
        getExpensesByBudget(budgetId = budgetId)
    }
}