package com.example.moneyware20.screen.homescreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.presentation.ui_model.BudgetUIModel

@Composable
fun BudgetList(budgetList: List<BudgetUIModel>) {
    Text(text = budgetList.toString())
}