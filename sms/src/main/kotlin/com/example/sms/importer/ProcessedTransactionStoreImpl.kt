package com.example.sms.importer

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first

class ProcessedTransactionStoreImpl(
    private val dataStore: DataStore<Preferences>
) : ProcessedTransactionStore {

    override suspend fun isProcessed(key: String): Boolean {
        val prefKey = stringPreferencesKey(key)
        val prefs = dataStore.data.first()
        return prefs[prefKey] == "1"
    }

    override suspend fun markProcessed(key: String) {
        val prefKey = stringPreferencesKey(key)
        dataStore.edit { prefs ->
            prefs[prefKey] = "1"
        }
    }
}
