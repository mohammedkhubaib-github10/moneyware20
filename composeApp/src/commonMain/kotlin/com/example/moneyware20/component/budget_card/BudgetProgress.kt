package com.example.moneyware20.component.budget_card

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
