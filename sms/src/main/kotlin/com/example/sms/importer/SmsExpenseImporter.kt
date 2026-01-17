package com.example.sms.importer

import android.util.Log
import com.example.domain.usecase.expense.CreateProcessedExpenseUsecase
import com.example.sms.helper.bucketTimestamp
import com.example.sms.helper.normalizeAmount
import com.example.sms.helper.normalizeMerchant
import com.example.sms.helper.sha256
import com.example.sms.helper.toExpense
import com.example.sms.parser.ParsedTransaction

class SmsExpenseImporter(
    private val createProcessedExpense: CreateProcessedExpenseUsecase
) {

    suspend fun import(
        parsed: ParsedTransaction?,
        userId: String,
        budgetId: String
    ) {
        //  Parse SMS
        if (parsed == null) return
        //  Create dedup key
        val key = TransactionKeyFactory.from(
            normalizeAmount(parsed.amount),
            normalizeMerchant(parsed.merchant),
            bucketTimestamp(parsed.timestamp)
        )
        val hash = sha256(key)
        Log.d("trans", parsed.toString())
        val expense =
            parsed.toExpense(
                userId = userId,
                budgetId = budgetId
            )
        createProcessedExpense(userId, hash, parsed.timestamp, expense)
    }
}
