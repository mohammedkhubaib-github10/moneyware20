package com.example.moneyware20.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButtonMenu
import androidx.compose.material3.FloatingActionButtonMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.moneyware20.component.header.MainHeader

@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {
            MainHeader(onActionIconClick = {}, onNavigationIconClick = {})
        },
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = { Fab { } }
    ) { it ->

    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Fab(onManualClick: () -> Unit) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val animatedColor by animateColorAsState(
        targetValue = if (expanded) Color(0xFF41817C) else Color(0xFF2C6B65)
    )
    FloatingActionButtonMenu(
        expanded = expanded,
        button = {
            ToggleFloatingActionButton(
                containerColor = { animatedColor },
                checked = expanded,
                onCheckedChange = { expanded = !expanded }
            ) {
                val icon = if (expanded) Icons.Filled.Close else Icons.Filled.Add
                Icon(icon, contentDescription = null, tint = Color.White)
            }
        }
    ) {
        // Manual Entry
        FloatingActionButtonMenuItem(
            onClick = { expanded = false; onManualClick() },
            icon = {
                Icon(
                    Icons.Filled.Edit,
                    contentDescription = "Manual Entry",
                    tint = Color.White
                )
            },
            text = { Text("Manual Entry", color = Color.White) },
            containerColor = Color(0xFF41817C)
        )
    }
}