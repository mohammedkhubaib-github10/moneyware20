package com.example.data.notification

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.example.domain.notification.BudgetNotificationStore
import kotlinx.coroutines.flow.first

class BudgetNotificationStoreImpl(
    private val dataStore: DataStore<Preferences>
) : BudgetNotificationStore {

    override suspend fun getLastNotifiedThreshold(budgetId: String): Int {
        val key = intPreferencesKey("budget_$budgetId")
        return dataStore.data.first()[key] ?: 0
    }

    override suspend fun setLastNotifiedThreshold(
        budgetId: String,
        threshold: Int
    ) {
        val key = intPreferencesKey("budget_$budgetId")
        dataStore.edit {
            it[key] = threshold
        }
    }
}