package com.example.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class BudgetDto(
    val name: String,
    val amount: Double
)