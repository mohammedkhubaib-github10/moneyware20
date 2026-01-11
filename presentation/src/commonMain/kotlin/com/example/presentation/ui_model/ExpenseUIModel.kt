package com.example.presentation.ui_model

import kotlinx.datetime.LocalDate

data class ExpenseUIModel(
    val expenseId: String,
    val expenseName: String,
    val date: LocalDate,
    val expenseAmount: String
)
