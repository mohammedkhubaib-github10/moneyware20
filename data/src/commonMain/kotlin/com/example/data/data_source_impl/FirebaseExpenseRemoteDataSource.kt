package com.example.data.data_source_impl

import com.example.data.data_source.ExpenseRemoteDataSource
import com.example.data.dto.ExpenseDto
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore

class FirebaseExpenseRemoteDataSource : ExpenseRemoteDataSource {
    private val db = Firebase.firestore
    private val expensesCollection = db.collection("expenses")
    override suspend fun addExpense(dto: ExpenseDto): String {
        val docRef = expensesCollection.add(dto)
        return docRef.id
    }

    override suspend fun updateExpense(
        expenseId: String,
        dto: ExpenseDto
    ) {
        expensesCollection.document(expenseId).set(dto)
    }

    override suspend fun getExpensesByUser(userId: String): List<Pair<String, ExpenseDto>> {
        val snapshot = expensesCollection
            .where { "userId" equalTo userId }
            .get()

        return snapshot.documents.map { doc ->
            val dto = doc.data<ExpenseDto>()
            doc.id to dto
        }
    }

    override suspend fun getExpensesByBudget(
        userId: String,
        budgetId: String
    ): List<Pair<String, ExpenseDto>> {
        val snapshot = expensesCollection
            .where { "userId" equalTo userId }
            .where { "budgetId" equalTo budgetId }
            .get()

        return snapshot.documents.map { doc ->
            val dto = doc.data<ExpenseDto>()
            doc.id to dto
        }
    }

    override suspend fun deleteExpense(expenseId: String) {
        expensesCollection
            .document(expenseId)
            .delete()
    }
}