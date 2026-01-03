package com.example.data.repository

import com.example.authentication.GoogleAuthHelper
import com.example.domain.entity.User
import com.example.domain.repository.AuthenticationRepository

class AuthenticationRepositoryImpl(private val googleAuthHelper: GoogleAuthHelper) :
    AuthenticationRepository {
    override suspend fun googleAuthentication(): User? {
        return googleAuthHelper.signInGoogle().fold(
            onSuccess = { userData ->
                User(
                    userId = userData.userId,
                    userName = userData.username,
                    profilePicUrl = userData.profilePictureUrl
                )
            },
            onFailure = {
                null
            }
        )
    }
}