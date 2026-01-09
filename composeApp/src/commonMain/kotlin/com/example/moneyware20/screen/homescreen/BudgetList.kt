package com.example.moneyware20.screen.homescreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.ui_model.BudgetUIModel

@Composable
fun BudgetList(budgetList: List<BudgetUIModel>) {
    LazyColumn(
        modifier = Modifier
    ) {
        items(budgetList) { budget ->
            BudgetCard(budget = budget)
        }
    }
}

@Composable
fun OverflowMenu(
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More",
                modifier = Modifier.size(32.dp)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Edit") },
                onClick = {
                    expanded = false
                    onEdit()
                }
            )
            DropdownMenuItem(
                text = { Text("Delete") },
                onClick = {
                    expanded = false
                    onDelete()
                }
            )
        }
    }
}

@Composable
fun BudgetCard(
    budget: BudgetUIModel,
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
                OverflowMenu(onEdit = {}, onDelete = {})
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
                    value = "₹ 2000",
                    modifier = Modifier.weight(1f)
                )
                BudgetInfoBox(
                    title = "Expense",
                    value = "₹ 200",
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

@Composable
fun BudgetInfoBox(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(
                color = Color(0xFFE5E5E5),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        Text(
            text = title,
            fontSize = 12.sp,
            color = Color.DarkGray
        )
        Spacer(modifier = Modifier.height(4.dp))
        AutoSizeText(
            text = value,
        )
    }
}

@Composable
fun AutoSizeText(
    text: String,
    modifier: Modifier = Modifier,
    maxFontSize: TextUnit = 16.sp,
    minFontSize: TextUnit = 12.sp
) {
    var textSize by remember { mutableStateOf(maxFontSize) }

    Text(
        text = text,
        fontSize = textSize,
        maxLines = 1,
        softWrap = false,
        onTextLayout = { result ->
            if (result.didOverflowWidth && textSize > minFontSize) {
                textSize *= 0.9f
            }
        },
        modifier = modifier
    )
}


@Composable
fun BudgetProgress(
    spentPercent: Float,
    modifier: Modifier = Modifier
) {
    val displayPercent = (spentPercent * 100).toInt()
    val ringProgress = spentPercent.coerceIn(0f, 1f)
    val ringColor = progressColor(spentPercent)

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        CircularProgressIndicator(
            progress = { ringProgress },
            strokeWidth = 10.dp,
            color = ringColor,
            trackColor = Color(0xFFEADCFB),
            modifier = Modifier.size(120.dp)
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$displayPercent%",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = "spent",
                fontSize = 12.sp,
                color = Color.DarkGray
            )
        }
    }
}

@Composable
fun progressColor(spentPercent: Float): Color {
    return when {
        spentPercent < 0.5f -> Color(0xFF4CAF50)    // Green
        spentPercent < 0.75f -> Color(0xFFFFC107)  // Yellow
        spentPercent <= 1f -> Color(0xFFFF9800)    // Orange
        else -> Color(0xFFF44336)                  // Red
    }
}
