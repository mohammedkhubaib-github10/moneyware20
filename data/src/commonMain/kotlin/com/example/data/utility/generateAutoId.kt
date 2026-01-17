package com.example.data.utility

import kotlin.random.Random

fun generateAutoId(): String {
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    return (1..20)
        .map { chars[Random.nextInt(chars.length)] }
        .joinToString("")
}