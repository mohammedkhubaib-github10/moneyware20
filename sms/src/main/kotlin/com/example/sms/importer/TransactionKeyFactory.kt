package com.example.sms.importer

object TransactionKeyFactory {

    fun from(
        amount: Double,
        merchant: String,
        timestamp: Long
    ): String {
        return "$amount|$merchant|$timestamp"
    }
}