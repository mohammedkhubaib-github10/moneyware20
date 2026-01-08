package com.example.presentation.ui_model

import kotlinx.serialization.Serializable

@Serializable
data class UserUIModel(
    val userId: String,
    val userName: String?,
    val profilePic: String?
)