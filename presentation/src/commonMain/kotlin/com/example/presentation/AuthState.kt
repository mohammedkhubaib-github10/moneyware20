package com.example.presentation

import com.example.presentation.ui_model.UserUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthState {

    private val _user = MutableStateFlow<UserUIModel?>(null)
    val user: StateFlow<UserUIModel?> = _user


    fun onLogin(user: UserUIModel) {
        _user.value = user
    }

    fun onLogout() {
        _user.value = null
    }
}
