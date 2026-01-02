package com.example.data.data_source

import com.example.data.dto.BudgetDto

interface BudgetRemoteDataSource {

    /* CREATE */
    suspend fun createBudget(dto: BudgetDto): String   //returns id

    /* READ */
    suspend fun getBudgetById(id: String): BudgetDto?

    suspend fun getBudgets(): List<Pair<String, BudgetDto>>  //return list of (id, dto)

    suspend fun isBudgetNameExists(name: String): Boolean

    /* UPDATE */
    suspend fun updateBudget(
        id: String,
        dto: BudgetDto
    )

    /* DELETE */
    suspend fun deleteBudget(id: String)
}
