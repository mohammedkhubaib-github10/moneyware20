package com.example.di

import BudgetNotificationHelper
import SmsInbox
import SmsViewModel
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.authentication.GoogleAuthHelper
import com.example.data.data_source.AuthenticationSource
import com.example.data.data_source_impl.AuthenticationSourceImpl
import com.example.data.notification.BudgetNotificationStoreImpl
import com.example.data.settings.AndroidSettingsRepository
import com.example.domain.AndroidBudgetThresholdNotifier
import com.example.domain.notification.BudgetNotificationStore
import com.example.domain.notification.BudgetThresholdNotifier
import com.example.domain.repository.SettingsRepository
import com.example.presentation.AutonomousEntry
import com.example.sms.importer.SmsExpenseImporter
import com.example.sms.parser.GenericDebitSmsParser
import com.example.sms.parser.SmsParser
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val Context.budgetDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "budget_notifications"
)
val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

// ---------- Qualifiers ----------
val BudgetDataStore = named("budgetDataStore")
val SettingsDataStore = named("settings")

// ---------- Module ----------

val androidViewModelModule = module {
    viewModel {
        SmsViewModel(
            autonomousEntry = get(),
            authState = get(),
            settingsRepository = get()
        )
    }
}
val androidDataStoreModule = module {

    single<DataStore<Preferences>>(BudgetDataStore) {
        androidContext().budgetDataStore
    }
    single<DataStore<Preferences>>(SettingsDataStore) {
        androidContext().settingsDataStore
    }

}
val androidSettingsModule = module {
    single<SettingsRepository> {
        AndroidSettingsRepository(get(SettingsDataStore))
    }
}
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

val smsModule = module {

    // Parser (pure)
    single<SmsParser> {
        GenericDebitSmsParser()
    }
    single {
        SmsInbox(androidContext())
    }

    single {
        AutonomousEntry(
            smsInbox = get(),
            importer = get(),
            smsParser = get(),
            getOrCreateMonthlyBudgetUsecase = get()
        )
    }


    // Importer (orchestrator)
    single {
        SmsExpenseImporter(
            get()
        )
    }
}
val notificationModule = module {

    single<BudgetNotificationStore> {
        BudgetNotificationStoreImpl(
            get(BudgetDataStore)
        )
    }

    single {
        BudgetNotificationHelper(androidContext())
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