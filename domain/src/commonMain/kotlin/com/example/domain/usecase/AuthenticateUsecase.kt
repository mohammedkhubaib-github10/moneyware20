package com.example.domain.usecase

import com.example.domain.entity.User
import com.example.domain.repository.AuthenticationRepository

class AuthenticateUsecase(private val authenticationRepository: AuthenticationRepository) {
    suspend operator fun invoke(): User? {
        return authenticationRepository.googleAuthentication()
    }
}