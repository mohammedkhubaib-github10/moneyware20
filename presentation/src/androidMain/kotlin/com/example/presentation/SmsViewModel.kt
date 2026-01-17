package com.example.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SmsViewModel(
    private val autonomousEntry: AutonomousEntry,
    private val authState: AuthState
) : ViewModel() {

    fun observeAndImport() {
        viewModelScope.launch {
            authState.user.collect { user ->
                if (user != null) {
                    launch(Dispatchers.IO) {
                        autonomousEntry.importCurrentMonthBankSms(user.userId)
                    }
                }
            }
        }
    }
}
