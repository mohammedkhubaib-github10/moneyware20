package com.example.sms.importer

interface ProcessedTransactionStore {

    suspend fun isProcessed(key: String): Boolean

    suspend fun markProcessed(key: String)
}
