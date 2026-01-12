package com.example.moneyware20.screen.expensescreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moneyware20.component.AutoSizeText
import com.example.moneyware20.component.OverflowMenu
import com.example.presentation.DialogMode
import com.example.presentation.ui_model.BudgetUIModel
import com.example.presentation.ui_model.ExpenseUIModel
import com.example.presentation.viewmodel.BudgetViewModel
import com.example.presentation.viewmodel.ExpenseViewModel
import com.example.ui.component.toDisplayText
import tertiaryColor
import kotlin.math.roundToInt

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
    Column(modifier = Modifier.fillMaxSize().offset(y = (-16).dp)) {
        BudgetSummaryCard(budget!!, modifier = Modifier.padding(16.dp))
        Box(modifier = Modifier.fillMaxSize()) {
            if (expenseList.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                ) {
                    items(expenseList) { expense ->
                        ExpenseCard(expense, expenseViewModel, budgetId)
                    }
                }
            } else Text(
                text = "No Result",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
        }
    }
}

@Composable
fun ExpenseCard(
    expense: ExpenseUIModel,
    expenseViewModel: ExpenseViewModel,
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
                onDelete = { expenseViewModel.onDeleteExpense(budgetId, expense.expenseId) })
        }
    }
}

@Composable
fun BudgetSummaryCard(
    budget: BudgetUIModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(140.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = tertiaryColor // teal-green
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            SummaryItem(
                label = "Balance",
                value = "₹ ${budget.balance}"
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                SummaryItem(
                    label = "Budget",
                    value = "₹ ${budget.budgetAmount}"
                )

                SummaryItem(
                    label = "Expenses",
                    value = "₹ ${budget.totalExpense}"
                )
            }
        }
    }
}

@Composable
private fun SummaryItem(
    label: String,
    value: String
) {
    Column {
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.85f),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(4.dp))
        AutoSizeText(
            text = value,
            fontWeight = FontWeight.Bold,
            textColor = Color.White,
            minFontSize = 16.sp,
            maxFontSize = 20.sp
        )
    }
}
