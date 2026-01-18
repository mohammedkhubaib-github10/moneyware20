package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.SettingsRepository
import com.example.presentation.mapper.toUIState
import com.example.presentation.ui_state.SettingsUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val settingsUIState: StateFlow<SettingsUIState> =
        settingsRepository.observeSettings()
            .map { it.toUIState() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SettingsUIState()
            )

    private val _budgetLimitDraft = MutableStateFlow("")
    val budgetLimitDraft = _budgetLimitDraft.asStateFlow()

    init {
        viewModelScope.launch {
            settingsUIState.collect { state ->
                _budgetLimitDraft.value = state.budgetLimit
            }
        }
    }

    fun toggleAutoEntry(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateAutoEntry(enabled)
        }
    }

    fun onBudgetLimitChange(amount: String) {
        _budgetLimitDraft.value = amount
    }

    fun onSave() {
        val amount = _budgetLimitDraft.value.toIntOrNull() ?: return
        viewModelScope.launch {
            settingsRepository.updateBudgetLimit(amount)
        }
    }
}
