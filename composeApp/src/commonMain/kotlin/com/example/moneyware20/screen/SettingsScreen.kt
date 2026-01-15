package com.example.moneyware20.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.moneyware20.component.header.Header

@Composable
fun SettingsScreen(onNavigation: () -> Unit) {
    var navigationConsumed by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Header(text = "Settings", onNavigationClick = {
                if (!navigationConsumed) {
                    navigationConsumed = true
                    onNavigation()
                }
            })
        },
        modifier = Modifier.fillMaxSize()
    ) {

    }
}