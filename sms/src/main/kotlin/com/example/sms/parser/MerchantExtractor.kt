package com.example.sms.parser

object MerchantExtractor {

    private val patterns = listOf(
        Regex("""to\s+([a-zA-Z0-9 .&_-]+?)(\s+upi|\s+ref|\s+txn|\.)""", RegexOption.IGNORE_CASE),
        Regex("""at\s+([a-zA-Z0-9 .&_-]+)""", RegexOption.IGNORE_CASE)
    )

    fun extract(message: String): String {
        for (pattern in patterns) {
            val match = pattern.find(message)
            if (match != null) {
                return match.groupValues[1].trim()
            }
        }
        return "Unknown"
    }
}
