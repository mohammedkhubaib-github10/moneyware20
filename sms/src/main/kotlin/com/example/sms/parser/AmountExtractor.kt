package com.example.sms.parser

object AmountExtractor {

    private val patterns = listOf(
        Regex("""rs\.?\s?(\d+(\.\d{1,2})?)""", RegexOption.IGNORE_CASE),
        Regex("""inr\s?(\d+(\.\d{1,2})?)""", RegexOption.IGNORE_CASE),
        Regex("""debited.*?(\d+(\.\d{1,2})?)""", RegexOption.IGNORE_CASE),
        Regex("""spent.*?(\d+(\.\d{1,2})?)""", RegexOption.IGNORE_CASE)
    )

    fun extract(message: String): Double? {
        for (pattern in patterns) {
            val match = pattern.find(message)
            if (match != null) {
                return match.groupValues[1].toDoubleOrNull()
            }
        }
        return null
    }
}
