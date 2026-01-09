package com.example.moneyware20.component.budget_card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
