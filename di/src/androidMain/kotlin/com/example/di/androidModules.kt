package com.example.di


import com.example.authentication.GoogleAuthHelper
import com.example.data.data_source.AuthenticationSource
import com.example.data.data_source_impl.AuthenticationSourceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidAuthModule = module {

    single {
        GoogleAuthHelper(
            context = androidContext()
        )
    }

    single<AuthenticationSource> {
        AuthenticationSourceImpl(
            googleAuthHelper = get()
        )
    }
}
