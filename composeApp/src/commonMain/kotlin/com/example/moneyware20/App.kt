package com.example.moneyware20

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.ui.screen.SplashScreen
import moneyware20.composeapp.generated.resources.Res
import moneyware20.composeapp.generated.resources.logo
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        SplashScreen(Res.drawable.logo)
    }
}