package com.example.domain.entity

import kotlinx.datetime.LocalDate

data class Expense(
    val expenseId: String,
    val expenseName: String,
    val date: LocalDate,
    val expenseAmount: Double,
    val budgetId: String
)
