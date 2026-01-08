package com.example.domain.usecase

import com.example.domain.entity.User
import com.example.domain.repository.AuthenticationRepository

class GetUserUsecase(private val authenticationRepository: AuthenticationRepository) {
    operator fun invoke(): User? {
        if (authenticationRepository.isUserSignedIn()) {
            return authenticationRepository.getCurrentUser()
        }
        return null
    }
}