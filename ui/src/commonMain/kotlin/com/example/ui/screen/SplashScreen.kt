package com.example.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun SplashScreen() {
    Column(modifier = Modifier) {
        Text(text = "Hello")
    }
}
