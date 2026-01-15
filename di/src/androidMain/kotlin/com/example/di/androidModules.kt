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
import com.example.presentation.AuthState
import com.example.sms.importer.ProcessedTransactionStore
import com.example.sms.importer.ProcessedTransactionStoreImpl
import com.example.sms.importer.SmsExpenseImporter
import com.example.sms.parser.GenericDebitSmsParser
import org.koin.android.ext.koin.androidContext
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
    single {
        GenericDebitSmsParser()
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
            parser = get(),
            createExpenseUsecase = get(),
            processedStore = get(),
            getOrCreateCurrentMonthBudget = get(),
            userIdProvider = { get<AuthState>().user.value?.userId }
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