package com.example.data.mapper

import com.example.data.dto.ExpenseDto
import com.example.domain.entity.Expense
import kotlinx.datetime.LocalDate

fun Expense.toDto() = ExpenseDto(
    expenseName = expenseName,
    date = date.toString(), // ISO-8601
    expenseAmount = expenseAmount,
    budgetId = budgetId,
    userId = userId
)

fun ExpenseDto.toDomain(id: String) = Expense(
    expenseId = id,
    expenseName = expenseName,
    date = LocalDate.parse(date),
    expenseAmount = expenseAmount,
    budgetId = budgetId,
    userId = userId
)
