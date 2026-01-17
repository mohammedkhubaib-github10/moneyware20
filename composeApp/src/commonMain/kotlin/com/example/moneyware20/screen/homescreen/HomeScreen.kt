package com.example.moneyware20.screen.homescreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moneyware20.component.dialog.BudgetDialog
import com.example.moneyware20.component.header.MainHeader
import com.example.presentation.DialogMode
import com.example.presentation.ui_model.BudgetUIModel
import com.example.presentation.ui_model.UserUIModel
import com.example.presentation.viewmodel.BudgetViewModel
import containerColor
import kotlinx.coroutines.launch
import moneyware20.composeapp.generated.resources.Res
import moneyware20.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource
import primaryColor


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeScreen(
    user: UserUIModel,
    viewModel: BudgetViewModel,
    onBudgetClick: (BudgetUIModel) -> Unit
) {
    val uiState by viewModel.budgetUIState.collectAsState()
    val budgetList by viewModel.budgetList.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    MoneywareDrawer(
        drawerState = drawerState,
        userName = if (user.userName != null) user.userName!! else "User",
        profileImage = painterResource(Res.drawable.logo),
        onSettingsClick = {},
        onSignOutClick = { viewModel.signOut() }
    ) {
        Scaffold(
            topBar = {
                MainHeader(onNavigationIconClick = {
                    scope.launch {
                        drawerState.open()
                    }
                })
            },
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = {
                FabBudget(viewModel)
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
                    if (budgetList.isNotEmpty()) BudgetList(budgetList, viewModel, onBudgetClick)
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
    }
    if (uiState.dialogState) {
        BudgetDialog(
            mode = uiState.dialogMode,
            budgetName = uiState.budgetName,
            budgetAmount = uiState.budgetAmount,
            onBudgetNameChange = { viewModel.onBudgetNameChange(it) },
            onBudgetAmountChange = { it ->
                val filtered = it
                    .filter { it.isDigit() || it == '.' }

                if (filtered.count { it == '.' } <= 1) {
                    viewModel.onBudgetAmountChange(filtered)
                }
            },
            onConfirmClick = {
                viewModel.setButton(false)
                if (uiState.dialogMode == DialogMode.ADD) {
                    viewModel.onAddBudget()
                } else {
                    viewModel.onEditBudget()
                }

            },
            onCancelClick = {
                viewModel.onCancel()
                viewModel.setDialog(false)
            },
            error = uiState.error,
            enabled = uiState.buttonState
        )
    }
}

