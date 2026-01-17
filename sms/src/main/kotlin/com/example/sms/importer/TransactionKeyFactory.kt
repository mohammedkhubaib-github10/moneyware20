package com.example.sms.importer

object TransactionKeyFactory {

    fun from(
        normAmount: String,
        normMerchant: String,
        bucketTimestamp: Long
    ): String {
        return "$normAmount|$normMerchant|$bucketTimestamp"
    }
}