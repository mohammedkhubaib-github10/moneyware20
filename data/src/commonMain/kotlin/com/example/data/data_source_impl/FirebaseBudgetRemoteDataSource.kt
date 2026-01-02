package com.example.data.data_source_impl

import com.example.data.data_source.BudgetRemoteDataSource
import com.example.data.dto.BudgetDto
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore

class FirebaseBudgetRemoteDataSource : BudgetRemoteDataSource {
    val db = Firebase.firestore
    override suspend fun createBudget(dto: BudgetDto): String {
        val docRef = db
            .collection("users")
            .document("khubaib")
            .collection("budgets")
            .add(dto)
        return docRef.id
    }

    override suspend fun getBudgetById(id: String): BudgetDto? {
        val snapshot = db
            .collection("users")
            .document("khubaib")
            .collection("budgets")
            .document(id)
            .get()
        if (snapshot.exists) return snapshot.data<BudgetDto>()
        return null
    }

    override suspend fun getBudgets(): List<Pair<String, BudgetDto>> {
        val snapshot = db
            .collection("users")
            .document("khubaib")
            .collection("budgets")
            .get()
        val list = snapshot.documents.map { doc ->
            val dto = doc.data<BudgetDto>()
            doc.id to dto
        }
        return list
    }

    override suspend fun isBudgetNameExists(name: String): Boolean {
        val snapshot = db
            .collection("users")
            .document("khubaib")
            .collection("budgets")
            .where { "name" equalTo name }
            .get()
        return snapshot.documents.isNotEmpty()
    }

    override suspend fun updateBudget(id: String, dto: BudgetDto) {
        db
            .collection("users")
            .document("khubaib")
            .collection("budgets")
            .document(id)
            .set(dto)
    }

    override suspend fun deleteBudget(id: String) {
        db
            .collection("users")
            .document("khubaib")
            .collection("budgets")
            .document(id)
            .delete()
    }
}