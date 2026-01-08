package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetUserUsecase
import com.example.presentation.AuthState
import com.example.presentation.mapper.toUIModel
import kotlinx.coroutines.launch

class SplashViewModel(
    private val getUserUsecase: GetUserUsecase,
    private val authState: AuthState
) : ViewModel() {

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            val user = getUserUsecase()?.toUIModel()
            authState.putUser(user)
        }
    }
}