package com.example.data.repository

import com.example.authentication.GoogleAuthHelper
import com.example.data.mapper.toDomain
import com.example.domain.entity.User
import com.example.domain.repository.AuthenticationRepository

class AuthenticationRepositoryImpl(private val googleAuthHelper: GoogleAuthHelper) :
    AuthenticationRepository {
    override suspend fun googleAuthentication(): User? {
        return googleAuthHelper.signInGoogle().fold(
            onSuccess = { userData ->
                userData.toDomain()
            },
            onFailure = {
                null
            }
        )
    }

    override fun isUserSignedIn(): Boolean {
        return googleAuthHelper.isSignedIn()
    }

    override fun getCurrentUser(): User? {
        return googleAuthHelper.getCurrentUser()?.toDomain()
    }

    override fun signOut() {
        googleAuthHelper.signOut()
    }

}