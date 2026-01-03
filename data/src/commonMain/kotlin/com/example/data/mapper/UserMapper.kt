package com.example.data.mapper

import com.example.data.dto.UserDto
import com.example.domain.entity.User

fun User.toDto() = UserDto(
    userId = userId,
    userName = userName,
    profilePic = profilePicUrl
)

fun UserDto.toDomain() = User(
    userId = userId,
    userName = userName,
    profilePicUrl = profilePic
)