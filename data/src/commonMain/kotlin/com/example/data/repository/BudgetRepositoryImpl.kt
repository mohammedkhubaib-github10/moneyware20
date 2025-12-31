package com.example.data.repository

import com.example.domain.entity.Budget
import com.example.domain.repository.BudgetRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore

class BudgetRepositoryImpl : BudgetRepository {
    override suspend fun createBudget(budget: Budget): Budget {
        val db = Firebase.firestore
        val b= hashMapOf(
            "Budget Name" to budget.budgetName,
            "Budget Amount" to budget.budgetAmount
        )
        val result = db.collection("user").add(b)
        return budget
    }

    override suspend fun updateBudget(budget: Budget) {
        TODO("Not yet implemented")
    }

    override suspend fun getBudgetById(budgetId: String): Budget? {
        TODO("Not yet implemented")
    }

    override suspend fun getBudgets(): List<Budget> {
        TODO("Not yet implemented")
    }

    override suspend fun isBudgetNameExists(name: String): Boolean {
        return false
    }

    override suspend fun deleteBudget(budgetId: String) {
        TODO("Not yet implemented")
    }
}