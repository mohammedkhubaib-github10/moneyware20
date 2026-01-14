package com.example.domain

import BudgetNotificationHelper
import android.Manifest
import androidx.annotation.RequiresPermission
import com.example.domain.notification.BudgetNotificationStore
import com.example.domain.notification.BudgetThresholdNotifier
import com.example.domain.repository.BudgetRepository
import com.example.domain.repository.ExpenseRepository

class AndroidBudgetThresholdNotifier(
    private val budgetRepository: BudgetRepository,
    private val expenseRepository: ExpenseRepository,
    private val notificationStore: BudgetNotificationStore,
    private val notificationHelper: BudgetNotificationHelper
) : BudgetThresholdNotifier {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override suspend fun onExpenseAdded(
        userId: String,
        budgetId: String
    ) {
        val budget =
            budgetRepository.getBudgetById(userId, budgetId)
                ?: return

        val expenses =
            expenseRepository.getExpensesByBudget(userId, budgetId)

        val totalSpent =
            expenses.sumOf { it.expenseAmount }

        val percentage =
            ((totalSpent * 100) / budget.budgetAmount).toInt()

        val lastNotified =
            notificationStore.getLastNotifiedThreshold(budgetId)


        when {
            percentage >= 90 && lastNotified < 90 -> {
                notificationHelper.showNotification(budget.budgetName, 90)
                notificationStore.setLastNotifiedThreshold(budgetId, 90)
            }

            percentage >= 50 && lastNotified < 50 -> {
                notificationHelper.showNotification(budget.budgetName, 50)
                notificationStore.setLastNotifiedThreshold(budgetId, 50)
            }
        }
    }
}
