package com.example.domain.notification

interface BudgetThresholdNotifier {
    suspend fun onExpenseAdded(
        userId: String,
        budgetId: String
    )
}
