package com.example.domain.usecase

import com.example.domain.entity.Budget
import com.example.domain.entity.Expense
import com.example.domain.usecase_model.BudgetSummary

class GetBudgetSummaryUsecase {
    operator fun invoke(budgets: List<Budget>, expenses: List<Expense>): List<BudgetSummary> {
        val budgetSummary = budgets.map { budget ->
            val budgetExpenses = expenses.filter {
                it.budgetId == budget.budgetId
            }
            budget.calculate(budgetExpenses)
        }
        return budgetSummary
    }

    fun Budget.calculate(expenses: List<Expense>): BudgetSummary {
        val totalExpense = expenses.sumOf { it.expenseAmount }
        val balance = budgetAmount - totalExpense
        val percentageUsed =
            if (budgetAmount > 0)
                (totalExpense / budgetAmount) * 100
            else 0.0
        return BudgetSummary(
            budgetId = budgetId,
            budgetName = budgetName,
            budgetAmount = budgetAmount,
            totalExpense = totalExpense,
            balance = balance,
            percentageUsed = percentageUsed
        )
    }
}