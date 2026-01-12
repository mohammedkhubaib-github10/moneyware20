package com.example.presentation.mapper

import com.example.domain.entity.Budget
import com.example.domain.entity.Expense
import com.example.presentation.ui_model.BudgetUIModel

fun Budget.toUIModel(): BudgetUIModel = BudgetUIModel(
    budgetId = budgetId,
    budgetName = budgetName,
    budgetAmount = budgetAmount.toString()
)
fun Budget.toBudgetCard(
    expenses: List<Expense>
): BudgetUIModel {

    val totalExpense = expenses.sumOf { it.expenseAmount }
    val balance = budgetAmount - totalExpense
    val percentageUsed =
        if (budgetAmount > 0)
            (totalExpense / budgetAmount) * 100
        else 0.0

    return BudgetUIModel(
        budgetId = budgetId,
        budgetName = budgetName,
        budgetAmount = budgetAmount.toString(),
        totalExpense = totalExpense.toString(),
        balance = balance.toString(),
        percentageUsed = percentageUsed.toString()
    )
}
