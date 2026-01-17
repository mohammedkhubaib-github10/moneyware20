package com.example.di

import BudgetNotificationHelper
import SmsInbox
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
import com.example.presentation.AutonomousEntry
import com.example.presentation.SmsViewModel
import com.example.sms.importer.ProcessedTransactionStore
import com.example.sms.importer.ProcessedTransactionStoreImpl
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

val Context.smsDedupDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "sms_dedup_store"
)

// ---------- Qualifiers ----------
val BudgetDataStore = named("budgetDataStore")
val SmsDedupDataStore = named("smsDedupDataStore")

// ---------- Module ----------

val androidViewModelModule = module {
    viewModel {
        SmsViewModel(get(), get())
    }
}
val androidDataStoreModule = module {

    single<DataStore<Preferences>>(BudgetDataStore) {
        androidContext().budgetDataStore
    }

    single<DataStore<Preferences>>(SmsDedupDataStore) {
        androidContext().smsDedupDataStore
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


    // Dedup store
    single<ProcessedTransactionStore> {
        ProcessedTransactionStoreImpl(
            get(SmsDedupDataStore)
        )
    }

    // Importer (orchestrator)
    single {
        SmsExpenseImporter(
            createExpenseUsecase = get(),
            processedStore = get()
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