package com.example.presentation

import SmsInbox
import com.example.domain.usecase.budget.GetOrCreateMonthlyBudgetUsecase
import com.example.sms.importer.SmsExpenseImporter
import com.example.sms.parser.SmsParser

class AutonomousEntry(
    private val smsInbox: SmsInbox,
    private val importer: SmsExpenseImporter,
    private val smsParser: SmsParser,
    private val getOrCreateMonthlyBudgetUsecase: GetOrCreateMonthlyBudgetUsecase
) {

    suspend fun importCurrentMonthBankSms(userId: String, budgetLimit: Int) {
        val smsList = smsInbox.getCurrentMonthBankDebitSms()

        val parsedList = smsList.map {
            smsParser.parse(it.body, it.timestamp)
        }
        val budget = getOrCreateMonthlyBudgetUsecase(userId, budgetLimit.toDouble())
        parsedList.forEach {
            importer.import(it, userId, budget.budgetId)
        }
    }
}