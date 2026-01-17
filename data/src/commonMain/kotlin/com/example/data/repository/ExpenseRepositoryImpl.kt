package com.example.data.repository

import com.example.data.data_source.ExpenseRemoteDataSource
import com.example.data.mapper.toDomain
import com.example.data.mapper.toDto
import com.example.domain.entity.Expense
import com.example.domain.repository.ExpenseRepository

class ExpenseRepositoryImpl(private val expenseRemoteDataSource: ExpenseRemoteDataSource) :
    ExpenseRepository {
    override suspend fun addExpense(expense: Expense): Expense {
        val expenseId = expenseRemoteDataSource.addExpense(expense.toDto())
        return expense.copy(expenseId = expenseId)
    }

    override suspend fun updateExpense(expense: Expense) {
        expenseRemoteDataSource.updateExpense(expenseId = expense.expenseId, expense.toDto())
    }

    override suspend fun getExpensesByUser(userId: String): List<Expense> {
        val dtoList = expenseRemoteDataSource.getExpensesByUser(userId)
        val newList = dtoList.map { (id, dto) -> dto.toDomain(id) }
        return newList
    }

    override suspend fun getExpensesByBudget(
        userId: String,
        budgetId: String
    ): List<Expense> {
        val dtoList = expenseRemoteDataSource.getExpensesByBudget(userId, budgetId)
        val newList = dtoList.map { (id, dto) -> dto.toDomain(id) }
        return newList
    }

    override suspend fun deleteExpense(expenseId: String) {
        expenseRemoteDataSource.deleteExpense(expenseId)
    }

    override suspend fun createProcessedExpense(
        userId: String,
        hash: String,
        smsTimeStamp: Long,
        expense: Expense
    ): Boolean {
        return expenseRemoteDataSource.createProcessedExpense(
            userId,
            hash,
            smsTimeStamp,
            expense.toDto()
        )
    }


}