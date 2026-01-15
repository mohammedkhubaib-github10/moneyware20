package com.example.sms.parser

object DebitFilter {

    private val debitKeywords = listOf(
        "debit",
        "debited",
        "spent",
        "purchase",
        "txn",
        "withdrawn"
    )

    fun isDebit(message: String): Boolean {
        val lower = message.lowercase()
        return debitKeywords.any { it in lower }
    }
}
