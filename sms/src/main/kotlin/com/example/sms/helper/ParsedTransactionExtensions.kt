package com.example.sms.helper

import com.example.domain.entity.Expense
import com.example.sms.parser.ParsedTransaction
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


fun ParsedTransaction.toLocalDate(): LocalDate {
    return Instant.fromEpochMilliseconds(timestamp)
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date
}

fun ParsedTransaction.toExpense(
    userId: String,
    budgetId: String
): Expense {
    return Expense(
        expenseId = "",
        expenseName = merchant,
        expenseAmount = amount,
        date = toLocalDate(),
        userId = userId,
        budgetId = budgetId
    )
}
