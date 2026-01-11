package com.example.presentation.mapper

import com.example.domain.entity.Expense
import com.example.presentation.ui_model.ExpenseUIModel

fun Expense.toUIModel() = ExpenseUIModel(
    expenseId = expenseId,
    expenseName = expenseName,
    expenseAmount = expenseAmount.toString(),
    date = date
)