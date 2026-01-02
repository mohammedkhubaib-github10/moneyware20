package com.example.di


import com.example.data.data_source.BudgetRemoteDataSource
import com.example.data.data_source_impl.FirebaseBudgetRemoteDataSource
import com.example.data.repository.BudgetRepositoryImpl
import com.example.domain.repository.BudgetRepository
import com.example.domain.usecase.Budget.CreateBudgetUsecase
import com.example.domain.usecase.Budget.GetBudgetUsecase
import com.example.domain.usecase.Budget.ValidateBudgetUsecase
import com.example.presentation.viewmodel.BudgetViewModel
import com.example.presentation.viewmodel.LoginViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::BudgetViewModel)
    viewModelOf(::LoginViewModel)

}

val dataModule = module {

    singleOf(::BudgetRepositoryImpl) { bind<BudgetRepository>() }
    singleOf(::FirebaseBudgetRemoteDataSource) { bind<BudgetRemoteDataSource>() }


}
val domainModule = module {

    factoryOf(::CreateBudgetUsecase)
    factoryOf(::ValidateBudgetUsecase)
    factoryOf(::GetBudgetUsecase)
}