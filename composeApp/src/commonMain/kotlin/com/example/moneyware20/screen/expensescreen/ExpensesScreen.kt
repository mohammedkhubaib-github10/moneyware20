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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moneyware20.component.header.Header
import com.example.presentation.DialogMode
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
    var navigationConsumed by remember { mutableStateOf(false) }

    val uiState by viewModel.expenseUIState.collectAsState()
    val expenseList by viewModel.expenseList.collectAsState()
    DisposableEffect(Unit) {
        onDispose {
            viewModel.clearState()
        }
    }
    Scaffold(
        topBar = {
            Header(text = budgetUIModel.budgetName, onNavigationClick = {
                if (!navigationConsumed) {
                    navigationConsumed = true
                    onNavigation()
                }
            }
            )
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
                if (expenseList.isNotEmpty()) ExpenseList(
                    expenseList,
                    viewModel,
                    budgetUIModel.budgetId
                )
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
                viewModel.setButton(false)

                if (uiState.dialogMode == DialogMode.ADD) {
                    viewModel.onAddExpense(budgetUIModel.budgetId)
                } else {
                    viewModel.onEditExpense(budgetUIModel.budgetId)
                }
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