package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.AuthenticateUsecase
import com.example.presentation.mapper.toUIModel
import com.example.presentation.ui_state.LoginUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authenticateUsecase: AuthenticateUsecase
) : ViewModel() {

    private val _loginUiState = MutableStateFlow(LoginUIState())
    val loginUiState: StateFlow<LoginUIState> = _loginUiState.asStateFlow()

    fun onNameChange(name: String) {
        _loginUiState.value = _loginUiState.value.copy(name = name)
    }

    fun onPhoneNoChange(phoneNo: String) {
        _loginUiState.value = _loginUiState.value.copy(phoneNo = phoneNo)
    }

    fun onGoogle() {
        viewModelScope.launch {
            _loginUiState.value = _loginUiState.value.copy(
                error = null
            )

            val user = authenticateUsecase()
                if(user !=  null) {
                    _loginUiState.value = _loginUiState.value.copy(
                        user = user.toUIModel()
                    )
                } else {
                    _loginUiState.value = _loginUiState.value.copy(
                        error = "Login Failed"
                    )
                }
        }
    }
}
