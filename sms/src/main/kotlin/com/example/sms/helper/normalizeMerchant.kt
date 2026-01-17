package com.example.sms.helper

fun normalizeMerchant(merchant: String): String {
    return merchant
        .uppercase()
        .replace(Regex("[^A-Z0-9 ]"), " ")   // keep spaces
        .replace(Regex("\\s+"), " ")         // collapse spaces
        .trim()
}
