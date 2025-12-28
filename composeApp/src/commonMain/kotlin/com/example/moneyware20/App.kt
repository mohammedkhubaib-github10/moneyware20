package com.example.moneyware20

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.moneyware20.navigation.NavigationRoot
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        NavigationRoot()
    }
}