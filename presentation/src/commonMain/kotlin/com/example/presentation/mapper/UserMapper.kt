package com.example.presentation.mapper

import com.example.domain.entity.User
import com.example.presentation.ui_model.UserUIModel

fun User.toUIModel() = UserUIModel(
    userId = userId,
    userName = userName,
    profilePic = profilePicUrl
)
