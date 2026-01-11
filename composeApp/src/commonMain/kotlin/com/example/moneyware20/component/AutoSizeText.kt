package com.example.moneyware20.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun AutoSizeText(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = Color.Unspecified,
    maxFontSize: TextUnit = 16.sp,
    minFontSize: TextUnit = 12.sp,
    fontWeight: FontWeight = FontWeight.Normal
) {
    var textSize by remember { mutableStateOf(maxFontSize) }

    Text(
        text = text,
        fontSize = textSize,
        color = textColor,
        fontWeight = fontWeight,
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