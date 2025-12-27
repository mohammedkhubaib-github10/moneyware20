package com.example.authentication

import kotlinx.coroutines.delay

suspend fun authentication(): String? {
    delay(4000)
    return "khubaib"
}