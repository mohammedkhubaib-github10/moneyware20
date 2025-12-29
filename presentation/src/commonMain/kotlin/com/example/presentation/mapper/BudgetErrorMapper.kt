package com.example.presentation.mapper

import com.example.domain.usecase.Budget.BudgetValidationResult

fun BudgetValidationResult.Error.toUiMessage(): String =
    when (this) {
        BudgetValidationResult.Error.InvalidAmount ->
            "Budget amount must be greater than zero"

        BudgetValidationResult.Error.DuplicateName ->
            "Budget name already exists"
    }
