package com.example.sms.helper

fun bucketTimestamp(ts: Long, windowMinutes: Int = 5): Long {
    return ts / (windowMinutes * 60 * 1000)
}
