package com.example.moneyware20.screen.homescreen

import BudgetDialog
import BudgetDialogMode
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.moneyware20.component.header.MainHeader
import com.example.presentation.viewmodel.BudgetViewModel
import kotlinx.coroutines.launch
import moneyware20.composeapp.generated.resources.Res
import moneyware20.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun HomeScreen(userName: String, viewModel: BudgetViewModel = koinViewModel()) {
    val uiState by viewModel.budgetUIState.collectAsState()
    val dialog by viewModel.dialogState.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var mode by rememberSaveable { mutableStateOf(BudgetDialogMode.ADD) }

    MoneywareDrawer(
        drawerState = drawerState,
        userName = "Mohammed Khubaib C",
        profileImage = painterResource(Res.drawable.logo),
        onSettingsClick = { /* navigate */ },
        onSignOutClick = { /* sign out */ }
    ) {
        Scaffold(
            topBar = {
                MainHeader(onActionIconClick = {}, onNavigationIconClick = {
                    scope.launch {
                        drawerState.open()
                    }
                })
            },
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = {
                Fab(viewModel)
            }
        ) { it ->
        }
    }
    if (dialog) {
        BudgetDialog(
            mode = mode,
            budgetName = uiState.budgetName,
            budgetAmount = uiState.budgetAmount,
            selectedType = uiState.budgetType,
            onBudgetNameChange = { viewModel.onBudgetNameChange(it) },
            onBudgetAmountChange = { viewModel.onBudgetAmountChange(it) },
            onBudgetTypeChange = { viewModel.onBudgetTypeChange(it) },
            onConfirmClick = {
                viewModel.onAddBudget()
                viewModel.toggleDialog(false)
            },
            onCancelClick = {
                viewModel.onCancel()
                viewModel.toggleDialog(false)
            },
        )
    }
}

