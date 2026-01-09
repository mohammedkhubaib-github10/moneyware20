package com.example.moneyware20.screen.homescreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moneyware20.component.budget_card.BudgetInfoBox
import com.example.moneyware20.component.budget_card.BudgetProgress
import com.example.presentation.ui_model.BudgetUIModel
import com.example.presentation.viewmodel.BudgetViewModel

@Composable
fun BudgetList(
    budgetList: List<BudgetUIModel>,
    viewModel: BudgetViewModel
) {
    LazyColumn(
        modifier = Modifier
    ) {
        items(budgetList) { budget ->
            BudgetCard(budget = budget, viewModel)
        }
    }
}

@Composable
fun BudgetCard(
    budget: BudgetUIModel,
    viewModel: BudgetViewModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
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
                    onEdit = { viewModel.editBudget(budget.budgetId) },
                    onDelete = { viewModel.deleteBudget(budget.budgetId) })
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Progress
            BudgetProgress(
                spentPercent = 20f,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(20.dp))

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
                    value = "₹ 0",
                    modifier = Modifier.weight(1f)
                )
                BudgetInfoBox(
                    title = "Balance",
                    value = "₹ 0",
                    modifier = Modifier.weight(1f)
                )
            }

        }
    }
}
