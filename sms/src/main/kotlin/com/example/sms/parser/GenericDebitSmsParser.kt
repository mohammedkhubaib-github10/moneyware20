package com.example.sms.parser

class GenericDebitSmsParser : SmsParser {

    override fun parse(
        message: String,
        timestamp: Long
    ): ParsedTransaction? {

        if (!DebitFilter.isDebit(message)) return null

        val amount = AmountExtractor.extract(message) ?: return null
        val merchant = MerchantExtractor.extract(message)
        val transaction = ParsedTransaction(
            amount = amount,
            merchant = merchant,
            timestamp = timestamp
        )
        return transaction
    }
}
