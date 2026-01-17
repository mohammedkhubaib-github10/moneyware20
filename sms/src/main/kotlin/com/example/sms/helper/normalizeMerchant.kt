package com.example.sms.helper

fun String.normalizeMerchant(): String {
    return this
        .lowercase()
        .replace(Regex("[^a-z0-9]"), "")
}
