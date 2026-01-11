package com.example.moneyware20.screen.expensescreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moneyware20.component.header.Header
import com.example.presentation.ui_model.BudgetUIModel
import com.example.presentation.viewmodel.ExpenseViewModel
import com.example.ui.component.ExpenseDialog
import containerColor
import primaryColor

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ExpensesScreen(
    budgetUIModel: BudgetUIModel,
    viewModel: ExpenseViewModel,
    onNavigation: () -> Unit
) {
    val uiState by viewModel.expenseUIState.collectAsState()
    val expenseList by viewModel.expenseList.collectAsState()
    Scaffold(
        topBar = {
            Header(text = budgetUIModel.budgetName, onNavigationClick = onNavigation)
        },
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FabExpenses(viewModel)
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            if (uiState.isLoading) {
                ContainedLoadingIndicator(
                    modifier = Modifier.padding(24.dp).align(Alignment.Center),
                    containerColor = containerColor,
                    indicatorColor = primaryColor
                )
            } else {
                if (expenseList.isNotEmpty()) ExpenseList(expenseList, viewModel)
                else Text(
                    text = "No Result",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )
            }
        }
    }
    if (uiState.dialogState) {
        ExpenseDialog(
            mode = uiState.dialogMode,
            expenseName = uiState.expenseName,
            expenseAmount = uiState.expenseAmount,
            selectedDate = uiState.date,
            onExpenseNameChange = { viewModel.onExpenseNameChange(it) },
            onExpenseAmountChange = {
                val filtered = it
                    .filter { it.isDigit() || it == '.' }

                if (filtered.count { it == '.' } <= 1) {
                    viewModel.onExpenseAmountChange(filtered)
                }
            },
            onDateChange = { viewModel.onDateChange(it) },
            onAddClick = {
                viewModel.onAddExpense(budgetUIModel.budgetId)
                viewModel.setButton(false)
            },
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