package com.example.di

import com.example.presentation.viewmodel.AuthViewModel
import com.example.presentation.viewmodel.BudgetViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val sharedModule = module {
    viewModelOf(::BudgetViewModel)
    viewModelOf(::AuthViewModel)
}