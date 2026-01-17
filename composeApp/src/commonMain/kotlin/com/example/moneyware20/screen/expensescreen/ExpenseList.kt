package com.example.moneyware20.screen.expensescreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.moneyware20.component.AutoSizeText
import com.example.moneyware20.component.OverflowMenu
import com.example.moneyware20.component.dialog.toDisplayText
import com.example.presentation.DialogMode
import com.example.presentation.ui_model.ExpenseUIModel
import com.example.presentation.viewmodel.BudgetViewModel
import com.example.presentation.viewmodel.ExpenseViewModel
import containerColor
import primaryColor
import kotlin.math.exp
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ExpenseList(
    expenseViewModel: ExpenseViewModel,
    budgetViewModel: BudgetViewModel,
    budgetId: String
) {
    val expenseList by expenseViewModel.expenseList.collectAsState()
    val budgetList by budgetViewModel.budgetList.collectAsState()
    val budget by remember(budgetList, budgetId) {
        derivedStateOf {
            budgetList.firstOrNull { it.budgetId == budgetId }
        }
    }
    val state = rememberPullToRefreshState()
    val uiState by expenseViewModel.expenseUIState.collectAsState()
    Column(modifier = Modifier.fillMaxSize().offset(y = (-16).dp)) {
        budget?.let {
            BudgetSummaryCard(it, modifier = Modifier.padding(16.dp))
        }
        PullToRefreshBox(
            isRefreshing = uiState.isRefreshing,
            state = state,
            onRefresh = {
                expenseViewModel.setRefreshing(true)
                expenseViewModel.refreshExpense(budgetId)
            },
            indicator = {
                PullToRefreshDefaults.LoadingIndicator(
                    state = state,
                    isRefreshing = uiState.isRefreshing,
                    containerColor = containerColor,
                    color = primaryColor,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            },
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                if (expenseList.isEmpty()) {
                    item {
                        Text(
                            text = "No Result",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 48.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    items(expenseList) { expense ->
                        ExpenseCard(
                            expense,
                            expenseViewModel,
                            budgetViewModel,
                            budgetId
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun ExpenseCard(
    expense: ExpenseUIModel,
    expenseViewModel: ExpenseViewModel,
    budgetViewModel: BudgetViewModel,
    budgetId: String
) {
    val amountColor = Color(0xFFE53935) // soft red
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5FAF9)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            /* -------- LEFT: Name + Date -------- */
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = expense.expenseName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = expense.date.toDisplayText(),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            /* -------- RIGHT: Amount -------- */
            AutoSizeText(
                text = "₹ ${expense.expenseAmount.toDouble().roundToInt()}",
                textColor = amountColor,
                fontWeight = FontWeight.Bold

            )
            OverflowMenu(
                onEdit = {
                    expenseViewModel.setDialog(true)
                    expenseViewModel.setExpenseDialogMode(DialogMode.EDIT)
                    expenseViewModel.onExpenseIdChange(expense.expenseId)
                    expenseViewModel.onExpenseNameChange(expense.expenseName)
                    expenseViewModel.onExpenseAmountChange(expense.expenseAmount)
                    expenseViewModel.onDateChange(expense.date)
                },
                onDelete = {
                    expenseViewModel.onDeleteExpense(
                        budgetId,
                        expense.expenseId
                    )
                    budgetViewModel.refreshBudgets()
                })
        }
    }
}

