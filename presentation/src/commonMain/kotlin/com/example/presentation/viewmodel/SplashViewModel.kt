package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetUserUsecase
import com.example.presentation.AuthState
import com.example.presentation.mapper.toUIModel
import com.example.presentation.ui_state.SplashUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val getUserUsecase: GetUserUsecase,
    private val authState: AuthState
) : ViewModel() {
    private val _uiState = MutableStateFlow(SplashUIState())
    val uiState: StateFlow<SplashUIState> = _uiState

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            val user = getUserUsecase()?.toUIModel()
            _uiState.value = _uiState.value.copy(user = user, isLoading = false)
            authState.putUser(user)
        }
    }
}