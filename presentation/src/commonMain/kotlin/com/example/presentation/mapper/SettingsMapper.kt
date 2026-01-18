package com.example.presentation.mapper

import com.example.domain.entity.AppSettings
import com.example.presentation.ui_state.SettingsUIState

fun AppSettings.toUIState(): SettingsUIState = SettingsUIState(
    autoEntryEnabled = autoEntryEnabled,
    budgetLimit = budgetLimit.toString()
)