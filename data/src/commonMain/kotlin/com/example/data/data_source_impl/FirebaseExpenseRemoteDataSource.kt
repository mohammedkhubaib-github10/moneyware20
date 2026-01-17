package com.example.data.data_source_impl

import com.example.data.data_source.ExpenseRemoteDataSource
import com.example.data.dto.ExpenseDto
import com.example.data.utility.generateAutoId
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.serialization.Serializable

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

    @Serializable
    data class GroupMetadata(
        val timestamps: List<Long> = emptyList()
    )

    override suspend fun createProcessedExpense(
        userId: String,
        hash: String,
        smsTimeStamp: Long,
        dto: ExpenseDto
    ): Boolean {
        // Reference to the metadata document (e.g. users/{uid}/processedGroups/{hash})
        val groupRef = db.collection("users")
            .document(userId)
            .collection("processedGroups")
            .document(hash)

        try {
            // FIXED: Removed "transaction ->" parameter.
            // We use 'this' (implicit) to access transaction methods.
            return db.runTransaction {

                // 1. READ: Get existing timestamps
                // 'get' is called directly on the transaction scope
                val snapshot = get(groupRef)

                val existingTimestamps = if (snapshot.exists) {
                    // Deserialize the list
                    snapshot.data<GroupMetadata>().timestamps
                } else {
                    emptyList()
                }

                // 2. LOGIC: Check for duplicates (Fuzzy Match: 15 minutes / 900,000ms)
                // This handles backup drift and clock skew
                val isDuplicate = existingTimestamps.any { savedTime ->
                    kotlin.math.abs(savedTime - smsTimeStamp) < 900_000
                }

                if (isDuplicate) {
                    return@runTransaction false
                }

                // 3. WRITE 1: Update the timestamp list
                val updatedTimestamps = existingTimestamps + smsTimeStamp
                set(groupRef, GroupMetadata(updatedTimestamps))

                // 4. WRITE 2: Create the actual expense
                val newExpenseRef = expensesCollection.document(generateAutoId())
                set(newExpenseRef, dto)

                return@runTransaction true
            }
        } catch (e: Exception) {
            // e.printStackTrace()
            return false
        }
    }
}