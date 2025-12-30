package com.example.moneyware20.screen.homescreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButtonMenu
import androidx.compose.material3.FloatingActionButtonMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.example.presentation.viewmodel.BudgetViewModel
import otherColor
import primaryColor

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Fab(viewModel: BudgetViewModel) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val animatedColor by animateColorAsState(
        targetValue = if (expanded) primaryColor else otherColor
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
            onClick = { expanded = false; viewModel.toggleDialog(true) },
            icon = {
                Icon(
                    Icons.Filled.Edit,
                    contentDescription = "Manual Entry",
                    tint = Color.White
                )
            },
            text = { Text("Manual Entry", color = Color.White) },
            containerColor = primaryColor
        )
    }
}



