package com.example.presentation.ui_state

import com.example.presentation.ui_model.UserUIModel

data class SplashUIState(
    val isLoading: Boolean = true,
    val user: UserUIModel? = null
)