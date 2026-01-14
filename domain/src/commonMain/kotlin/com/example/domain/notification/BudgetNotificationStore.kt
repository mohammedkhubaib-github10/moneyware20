package com.example.domain.notification

interface BudgetNotificationStore {
    suspend fun getLastNotifiedThreshold(budgetId: String): Int
    suspend fun setLastNotifiedThreshold(budgetId: String, threshold: Int)
}
