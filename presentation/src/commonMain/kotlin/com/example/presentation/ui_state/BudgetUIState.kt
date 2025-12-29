package com.example.presentation.ui_state

import com.example.presentation.ui_state.BudgetType

data class BudgetUIState(
    val budgetName: String = "",
    val budgetAmount: String = "",
    val budgetType: BudgetType = BudgetType.MANUAL
)