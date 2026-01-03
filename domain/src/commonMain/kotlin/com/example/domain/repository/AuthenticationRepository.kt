package com.example.domain.repository

import com.example.domain.entity.User

interface AuthenticationRepository {
    suspend fun googleAuthentication(): User?
}