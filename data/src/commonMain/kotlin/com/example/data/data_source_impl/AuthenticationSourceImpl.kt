package com.example.data.data_source_impl

import com.example.authentication.GoogleAuthHelper
import com.example.data.data_source.AuthenticationSource
import com.example.data.mapper.toDomain
import com.example.domain.entity.User

class AuthenticationSourceImpl(private val googleAuthHelper: GoogleAuthHelper) :
    AuthenticationSource {
    override suspend fun signInGoogle(): User? {
        return googleAuthHelper.signInGoogle().fold(onSuccess = { userData ->
            userData.toDomain()
        }, onFailure = {
            null
        })
    }

    override fun isSignedIn(): Boolean {
        return googleAuthHelper.isSignedIn()

    }

    override fun getCurrentUser(): User? {
        return googleAuthHelper.getCurrentUser()?.toDomain()
    }

    override fun signOut() {
        googleAuthHelper.signOut()
    }
}