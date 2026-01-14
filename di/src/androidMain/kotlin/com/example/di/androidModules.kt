package com.example.di


import BudgetNotificationHelper
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.authentication.GoogleAuthHelper
import com.example.data.data_source.AuthenticationSource
import com.example.data.data_source_impl.AuthenticationSourceImpl
import com.example.domain.AndroidBudgetThresholdNotifier
import com.example.domain.BudgetNotificationStoreImpl
import com.example.domain.notification.BudgetNotificationStore
import com.example.domain.notification.BudgetThresholdNotifier
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val Context.budgetDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "budget_notifications"
)


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
    single<DataStore<Preferences>> {
        androidContext().budgetDataStore
    }
    single<BudgetNotificationStore> {
        BudgetNotificationStoreImpl(get())
    }

    single {
        BudgetNotificationHelper(get())
    }

    single<BudgetThresholdNotifier> {
        AndroidBudgetThresholdNotifier(
            budgetRepository = get(),
            expenseRepository = get(),
            notificationStore = get(),
            notificationHelper = get()
        )
    }
}
