package com.example.di


import com.example.data.data_source.BudgetRemoteDataSource
import com.example.data.data_source.ExpenseRemoteDataSource
import com.example.data.data_source_impl.FirebaseBudgetRemoteDataSource
import com.example.data.data_source_impl.FirebaseExpenseRemoteDataSource
import com.example.data.repository.AuthenticationRepositoryImpl
import com.example.data.repository.BudgetRepositoryImpl
import com.example.data.repository.ExpenseRepositoryImpl
import com.example.domain.repository.AuthenticationRepository
import com.example.domain.repository.BudgetRepository
import com.example.domain.repository.ExpenseRepository
import com.example.domain.usecase.AuthenticateUsecase
import com.example.domain.usecase.GetBudgetSummaryUsecase
import com.example.domain.usecase.GetUserUsecase
import com.example.domain.usecase.SignOutUsecase
import com.example.domain.usecase.budget.CreateBudgetUsecase
import com.example.domain.usecase.budget.DeleteBudgetUsecase
import com.example.domain.usecase.budget.GetBudgetUsecase
import com.example.domain.usecase.budget.GetOrCreateMonthlyBudgetUsecase
import com.example.domain.usecase.budget.UpdateBudgetUsecase
import com.example.domain.usecase.budget.ValidateBudgetUsecase
import com.example.domain.usecase.expense.CreateExpenseUsecase
import com.example.domain.usecase.expense.DeleteExpenseUsecase
import com.example.domain.usecase.expense.GetExpenseUsecase
import com.example.domain.usecase.expense.UpdateExpenseUsecase
import com.example.presentation.AuthState
import com.example.presentation.viewmodel.BudgetViewModel
import com.example.presentation.viewmodel.ExpenseViewModel
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
    viewModelOf(::ExpenseViewModel)

    singleOf(::AuthState)
}

val dataModule = module {

    singleOf(::BudgetRepositoryImpl) { bind<BudgetRepository>() }
    singleOf(::FirebaseBudgetRemoteDataSource) { bind<BudgetRemoteDataSource>() }
    singleOf(::FirebaseExpenseRemoteDataSource) { bind<ExpenseRemoteDataSource>() }
    singleOf(::AuthenticationRepositoryImpl) { bind<AuthenticationRepository>() }
    singleOf(::ExpenseRepositoryImpl) { bind<ExpenseRepository>() }

}
val domainModule = module {

    factoryOf(::CreateBudgetUsecase)
    factoryOf(::ValidateBudgetUsecase)
    factoryOf(::GetBudgetUsecase)
    factoryOf(::AuthenticateUsecase)
    factoryOf(::GetUserUsecase)
    factoryOf(::SignOutUsecase)
    factoryOf(::DeleteBudgetUsecase)
    factoryOf(::UpdateBudgetUsecase)
    factoryOf(::CreateExpenseUsecase)
    factoryOf(::GetExpenseUsecase)
    factoryOf(::UpdateExpenseUsecase)
    factoryOf(::DeleteExpenseUsecase)
    factoryOf(::GetBudgetSummaryUsecase)
    factoryOf(::GetOrCreateMonthlyBudgetUsecase)
}