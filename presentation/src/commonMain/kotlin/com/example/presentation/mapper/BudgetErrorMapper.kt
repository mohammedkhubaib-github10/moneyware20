package com.example.presentation.mapper

import com.example.domain.usecase.budget.BudgetValidationResult

fun BudgetValidationResult.Error.toUiMessage(): String =
    when (this) {
        BudgetValidationResult.Error.InvalidAmount ->
            "Budget amount must be greater than zero"

        BudgetValidationResult.Error.DuplicateName ->
            "Budget name already exists"
    }
