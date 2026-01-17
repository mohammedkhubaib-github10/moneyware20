package com.example.sms.sms_inbox

data class SmsMessage(
    val id: Long,
    val address: String?,
    val body: String,
    val timestamp: Long,
    val type: Int
)
