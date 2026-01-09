package com.example.moneyware20.screen.expensescreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.moneyware20.component.header.Header
import com.example.presentation.ui_model.BudgetUIModel
import com.example.presentation.viewmodel.ExpenseViewModel

@Composable
fun ExpensesScreen(
    budgetUIModel: BudgetUIModel,
    viewModel: ExpenseViewModel,
    onNavigation: () -> Unit
) {

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
}