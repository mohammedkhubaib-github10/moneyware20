package com.example.sms.parser

data class ParsedTransaction(
    val amount: Double,
    val merchant: String,
    val timestamp: Long
)
