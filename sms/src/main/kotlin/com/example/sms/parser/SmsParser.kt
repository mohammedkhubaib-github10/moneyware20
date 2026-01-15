package com.example.sms.parser

interface SmsParser {
    fun parse(message: String, timestamp: Long): ParsedTransaction?
}
