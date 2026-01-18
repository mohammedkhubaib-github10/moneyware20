package com.example.moneyware20.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.moneyware20.component.MoneywareTextField
import com.example.moneyware20.component.header.Header
import com.example.presentation.viewmodel.SettingsViewModel
import primaryColor

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onNavigation: () -> Unit
) {
    var navigationConsumed by remember { mutableStateOf(false) }

    val uiState by viewModel.settingsUIState.collectAsState()
    val budgetLimit by viewModel.budgetLimitDraft.collectAsState()

    Scaffold(
        topBar = {
            Header(
                text = "Settings",
                onNavigationClick = {
                    if (!navigationConsumed) {
                        navigationConsumed = true
                        onNavigation()
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {


            uiState.autoEntryEnabled?.let { enabled ->
                SettingsItem(
                    text = "Enable Auto-Entry from SMS",
                    checked = enabled,
                    onCheckedChange = viewModel::toggleAutoEntry
                )
                if (enabled) {
                    BudgetLimitEntry(
                        text = budgetLimit,
                        onSave = viewModel::onSave,
                        onBudgetLimitChange = { it ->
                            val filtered = it
                                .filter { it.isDigit() || it == '.' }

                            if (filtered.count { it == '.' } <= 1) {
                                viewModel.onBudgetLimitChange(filtered)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun BudgetLimitEntry(
    text: String,
    onSave: () -> Unit,
    onBudgetLimitChange: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MoneywareTextField(
            text = text,
            hint = "Budget Limit",
            onValueChange = {
                onBudgetLimitChange(it)
            },
            modifier = Modifier.weight(2f),
            keyboardType = KeyboardType.Number
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = {
                onSave()
                focusManager.clearFocus()
                keyboardController?.hide()
            },
            colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
        ) {
            Text(text = "Save")
        }

    }
}

@Composable
fun SettingsItem(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5FAF9)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.weight(1f))

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedTrackColor = primaryColor
                ),
                thumbContent = {
                    if (checked) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "",
                            modifier = Modifier.size(SwitchDefaults.IconSize)
                        )
                    }
                }
            )
        }
    }
}
