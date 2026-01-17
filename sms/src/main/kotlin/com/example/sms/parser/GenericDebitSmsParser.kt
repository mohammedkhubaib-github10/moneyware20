package com.example.sms.parser

import android.util.Log
import com.example.sms.helper.normalizeMerchant

class GenericDebitSmsParser : SmsParser {

    override fun parse(
        message: String,
        timestamp: Long
    ): ParsedTransaction? {

        if (!DebitFilter.isDebit(message)) return null

        val amount = AmountExtractor.extract(message) ?: return null
        val merchant = MerchantExtractor.extract(message)
        Log.d("merch", merchant)
        val transaction = ParsedTransaction(
            amount = amount,
            merchant = merchant.normalizeMerchant(),
            timestamp = timestamp
        )
        return transaction
    }
}
