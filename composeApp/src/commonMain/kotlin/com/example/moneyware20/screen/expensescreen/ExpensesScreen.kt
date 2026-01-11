package com.example.moneyware20.screen.expensescreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.moneyware20.component.header.Header
import com.example.presentation.ui_model.BudgetUIModel
import com.example.presentation.viewmodel.ExpenseViewModel
import com.example.ui.component.ExpenseDialog
import kotlinx.datetime.LocalDate

@Composable
fun ExpensesScreen(
    budgetUIModel: BudgetUIModel,
    viewModel: ExpenseViewModel,
    onNavigation: () -> Unit
) {
    val uiState by viewModel.expenseUIState.collectAsState()
    Scaffold(
        topBar = {
            Header(text = budgetUIModel.budgetName, onNavigationClick = onNavigation)
        },
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FabExpenses(viewModel)
        }
    ) {

    }
    if (uiState.dialogState) {
        ExpenseDialog(
            mode = uiState.dialogMode,
            expenseName = uiState.expenseName,
            expenseAmount = uiState.expenseAmount,
            selectedDate = LocalDate.fromEpochDays(1),
            onExpenseNameChange = { viewModel.onExpenseNameChange(it) },
            onExpenseAmountChange = { viewModel.onExpenseAmountChange(it) },
            onDateChange = {},
            onAddClick = {},
            onCancelClick = {
                viewModel.setDialog(false)
                viewModel.onCancel()
            },
            enabled = uiState.buttonState,
            setDatePicker = { viewModel.setDatePicker(it) },
            datePickerState = uiState.datePickerState
        )
    }
}