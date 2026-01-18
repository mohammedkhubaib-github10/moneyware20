package com.example.data.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.example.domain.entity.AppSettings
import com.example.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AndroidSettingsRepository(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {
    val AUTO_ENTRY = booleanPreferencesKey("auto_entry")
    val BUDGET_LIMIT = intPreferencesKey("budget_limit")

    override fun observeSettings(): Flow<AppSettings> =
        dataStore.data.map { prefs ->
            AppSettings(
                autoEntryEnabled = prefs[AUTO_ENTRY] ?: true,
                budgetLimit = prefs[BUDGET_LIMIT] ?: 10000
            )
        }


    override suspend fun updateAutoEntry(enabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[AUTO_ENTRY] = enabled
        }
    }

    override suspend fun updateBudgetLimit(amount: Int) {
        dataStore.edit { prefs ->
            prefs[BUDGET_LIMIT] = amount
        }
    }

}