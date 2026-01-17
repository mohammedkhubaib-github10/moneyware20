package com.example.sms.helper

import java.util.Locale

fun normalizeAmount(amount: Double): String {
    return String.format(Locale.US, "%.2f", amount)
}
