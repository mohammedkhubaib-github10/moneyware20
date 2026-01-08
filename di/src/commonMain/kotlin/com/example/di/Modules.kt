package com.example.di


import com.example.data.data_source.AuthenticationSource
import com.example.data.data_source.BudgetRemoteDataSource
import com.example.data.data_source_impl.AuthenticationSourceImpl
import com.example.data.data_source_impl.FirebaseBudgetRemoteDataSource
import com.example.data.repository.AuthenticationRepositoryImpl
import com.example.data.repository.BudgetRepositoryImpl
import com.example.domain.repository.AuthenticationRepository
import com.example.domain.repository.BudgetRepository
import com.example.domain.usecase.AuthenticateUsecase
import com.example.domain.usecase.Budget.CreateBudgetUsecase
import com.example.domain.usecase.Budget.GetBudgetUsecase
import com.example.domain.usecase.Budget.ValidateBudgetUsecase
import com.example.domain.usecase.SignOutUsecase
import com.example.domain.usecase.expense.GetUserUsecase
import com.example.presentation.viewmodel.BudgetViewModel
import com.example.presentation.viewmodel.LoginViewModel
import com.example.presentation.viewmodel.SplashViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::BudgetViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::SplashViewModel)

}

val dataModule = module {

    singleOf(::BudgetRepositoryImpl) { bind<BudgetRepository>() }
    singleOf(::FirebaseBudgetRemoteDataSource) { bind<BudgetRemoteDataSource>() }
    singleOf(::AuthenticationSourceImpl) { bind<AuthenticationSource>() }
    singleOf(::AuthenticationRepositoryImpl) { bind<AuthenticationRepository>() }

}
val domainModule = module {

    factoryOf(::CreateBudgetUsecase)
    factoryOf(::ValidateBudgetUsecase)
    factoryOf(::GetBudgetUsecase)
    factoryOf(::AuthenticateUsecase)
    factoryOf(::GetUserUsecase)
    factoryOf(::SignOutUsecase)
}