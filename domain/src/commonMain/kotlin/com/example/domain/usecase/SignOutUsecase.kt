package com.example.domain.usecase

import com.example.domain.repository.AuthenticationRepository

class SignOutUsecase(private val authenticationRepository: AuthenticationRepository) {
    operator fun invoke() {
        authenticationRepository.signOut()
    }
}