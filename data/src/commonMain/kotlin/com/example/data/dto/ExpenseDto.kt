package com.example.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ExpenseDto(
    val expenseName: String = "",
    val date: String = "", // "2026-01-09"
    val expenseAmount: Double = 0.0,
    val budgetId: String = "",
    val userId: String = ""
)
