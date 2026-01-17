package com.example.moneyware20.screen.homescreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moneyware20.component.OverflowMenu
import com.example.moneyware20.component.budget_card.BudgetInfoBox
import com.example.moneyware20.component.budget_card.BudgetProgress
import com.example.presentation.DialogMode
import com.example.presentation.ui_model.BudgetUIModel
import com.example.presentation.viewmodel.BudgetViewModel
import kotlin.math.roundToInt

@Composable
fun BudgetList(
    budgetList: List<BudgetUIModel>,
    viewModel: BudgetViewModel,
    onClick: (BudgetUIModel) -> Unit
) {
    val state = rememberPullToRefreshState()
    val uiState by viewModel.budgetUIState.collectAsState()
    PullToRefreshBox(
        isRefreshing = uiState.isRefreshing,
        state = state,
        onRefresh = {
            viewModel.refreshBudgets()
        },
    ) {
        LazyColumn(
            modifier = Modifier
        ) {
            items(budgetList) { budget ->
                BudgetCard(budget, viewModel, onClick)
            }
        }
    }
}

@Composable
fun BudgetCard(
    budget: BudgetUIModel,
    viewModel: BudgetViewModel,
    onClick: (BudgetUIModel) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = { onClick(budget) }),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5FAF9)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = budget.budgetName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
                Spacer(modifier = Modifier.weight(1f))
                OverflowMenu(
                    onEdit = {
                        viewModel.setDialog(true)
                        viewModel.setBudgetDialogMode(DialogMode.EDIT)
                        viewModel.onBudgetIdChange(budgetId = budget.budgetId)
                        viewModel.onBudgetNameChange(budget.budgetName)
                        viewModel.onBudgetAmountChange(budget.budgetAmount)
                    },
                    onDelete = { viewModel.deleteBudget(budget.budgetId) })
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Progress
            BudgetProgress(
                spentPercent = budget.percentageUsed.toFloat() / 100,
                modifier = Modifier.align(Alignment.CenterHorizontally).offset(y = -20.dp)
            )


            // Info row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                BudgetInfoBox(
                    title = "Budget",
                    value = "₹ ${budget.budgetAmount}",
                    modifier = Modifier.weight(1f)
                )
                BudgetInfoBox(
                    title = "Expense",
                    value = "₹ ${budget.totalExpense.toDouble().roundToInt()}",
                    modifier = Modifier.weight(1f)
                )
                BudgetInfoBox(
                    title = "Balance",
                    value = "₹ ${budget.balance.toDouble().roundToInt()}",
                    modifier = Modifier.weight(1f)
                )
            }

        }
    }
}
