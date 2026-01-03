package com.example.presentation.ui_state

import com.example.presentation.ui_model.UserUIModel

data class LoginUIState(
    val user: UserUIModel? = null,
    val error: String? = null,
    val name: String = "",
    val phoneNo: String = ""
)

