package com.example.domain.repository

import com.example.domain.entity.AppSettings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun observeSettings(): Flow<AppSettings>
    suspend fun updateAutoEntry(enabled: Boolean)
    suspend fun updateBudgetLimit(amount: Int)
}