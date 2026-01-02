package com.example.data.mapper

import com.example.data.dto.BudgetDto
import com.example.domain.entity.Budget

fun Budget.toDto(): BudgetDto =
    BudgetDto(
        name = budgetName,
        amount = budgetAmount
    )

fun BudgetDto.toDomain(id: String): Budget =
    Budget(
        budgetId = id,
        budgetName = name,
        budgetAmount = amount
    )
