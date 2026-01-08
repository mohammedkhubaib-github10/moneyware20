package com.example.data.data_source

import com.example.domain.entity.User

interface AuthenticationSource {
    suspend fun signInGoogle(): User?
    fun isSignedIn(): Boolean
    fun getCurrentUser(): User?
    fun signOut()
}