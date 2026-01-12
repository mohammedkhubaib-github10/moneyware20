package com.example.data.data_source

import com.example.data.dto.BudgetDto

interface BudgetRemoteDataSource {

    /* CREATE */
    suspend fun createBudget(userId: String, dto: BudgetDto): String   //returns id

    /* READ */
    suspend fun getBudgetById(userId: String, id: String): BudgetDto?

    suspend fun getBudgets(userId: String): List<Pair<String, BudgetDto>>  //return list of (id, dto)

    suspend fun isBudgetNameExists(userId: String, name: String): Pair<Boolean, String?>

    /* UPDATE */
    suspend fun updateBudget(
        userId: String,
        id: String,
        dto: BudgetDto
    )

    /* DELETE */
    suspend fun deleteBudget(userId: String, id: String)
}
