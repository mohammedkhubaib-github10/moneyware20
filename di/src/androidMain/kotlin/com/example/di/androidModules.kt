package com.example.di


import com.example.authentication.GoogleAuthHelper
import com.example.data.repository.AuthenticationRepositoryImpl
import com.example.domain.repository.AuthenticationRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidAuthModule = module {

    single {
        GoogleAuthHelper(
            context = androidContext()
        )
    }

    single<AuthenticationRepository> {
        AuthenticationRepositoryImpl(
            googleAuthHelper = get()
        )
    }
}
