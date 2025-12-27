package com.example.presentation.ui_state

import com.example.domain.entity.BudgetType

data class BudgetUIState(
    val budgetName: String = "",
    val budgetAmount: String = "",
    val budgetType: BudgetType = BudgetType.MANUAL
)