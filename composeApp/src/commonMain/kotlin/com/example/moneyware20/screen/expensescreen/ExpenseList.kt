package com.example.moneyware20.screen.expensescreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.moneyware20.component.AutoSizeText
import com.example.presentation.ui_model.ExpenseUIModel
import com.example.presentation.viewmodel.ExpenseViewModel
import com.example.ui.component.toDisplayText
import kotlin.math.roundToInt

@Composable
fun ExpenseList(expenseList: List<ExpenseUIModel>, viewModel: ExpenseViewModel) {
    LazyColumn(
        modifier = Modifier
    ) {
        items(expenseList) { expense ->
            ExpenseCard(expense, viewModel)
        }
    }
}

@Composable
fun ExpenseCard(
    expenseUIModel: ExpenseUIModel,
    viewModel: ExpenseViewModel
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
                    text = expenseUIModel.expenseName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = expenseUIModel.date.toDisplayText(),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            /* -------- RIGHT: Amount -------- */
            AutoSizeText(
                text = "₹ ${expenseUIModel.expenseAmount.toDouble().roundToInt()}",
                textColor = amountColor,
                fontWeight = FontWeight.Bold

            )
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "menu",
                modifier = Modifier.clickable {})
        }
    }
}
