package com.example.data.repository

import com.example.data.data_source.AuthenticationSource
import com.example.domain.entity.User
import com.example.domain.repository.AuthenticationRepository

class AuthenticationRepositoryImpl(private val authenticationSource: AuthenticationSource) :
    AuthenticationRepository {
    override suspend fun googleAuthentication(): User? {
        return authenticationSource.signInGoogle()
    }

    override fun isUserSignedIn(): Boolean {
        return authenticationSource.isSignedIn()
    }

    override fun getCurrentUser(): User? {
        return authenticationSource.getCurrentUser()
    }

    override fun signOut() {
        authenticationSource.signOut()
    }

}