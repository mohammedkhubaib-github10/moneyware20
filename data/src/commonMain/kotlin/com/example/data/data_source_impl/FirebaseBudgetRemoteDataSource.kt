package com.example.data.data_source_impl

import com.example.data.data_source.BudgetRemoteDataSource
import com.example.data.dto.BudgetDto
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore

class FirebaseBudgetRemoteDataSource : BudgetRemoteDataSource {
    val db = Firebase.firestore
    override suspend fun createBudget(userId: String, dto: BudgetDto): String {
        val docRef = db
            .collection("users")
            .document(userId)
            .collection("budgets")
            .add(dto)
        return docRef.id
    }

    override suspend fun getBudgetById(userId: String, id: String): BudgetDto? {
        val snapshot = db
            .collection("users")
            .document(userId)
            .collection("budgets")
            .document(id)
            .get()
        if (snapshot.exists) return snapshot.data<BudgetDto>()
        return null
    }

    override suspend fun getBudgets(userId: String): List<Pair<String, BudgetDto>> {
        val snapshot = db
            .collection("users")
            .document(userId)
            .collection("budgets")
            .get()
        val list = snapshot.documents.map { doc ->
            val dto = doc.data<BudgetDto>()
            doc.id to dto
        }
        return list
    }

    override suspend fun isBudgetNameExists(userId: String, name: String): Boolean {
        val snapshot = db
            .collection("users")
            .document(userId)
            .collection("budgets")
            .where { "name" equalTo name }
            .get()
        return snapshot.documents.isNotEmpty()
    }

    override suspend fun updateBudget(userId: String, id: String, dto: BudgetDto) {
        db
            .collection("users")
            .document(userId)
            .collection("budgets")
            .document(id)
            .set(dto)
    }

    override suspend fun deleteBudget(userId: String, id: String) {
        db
            .collection("users")
            .document(userId)
            .collection("budgets")
            .document(id)
            .delete()
    }
}