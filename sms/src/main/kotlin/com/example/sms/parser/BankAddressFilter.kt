package com.example.sms.parser

object BankAddressFilter {

    private val bankKeywords = listOf(
        "HDFC",
        "ICICI",
        "AXIS",
        "SBI",
        "KOTAK",
        "PNB",
        "YES",
        "IDFC",
        "CANARA",
        "BOB",
        "INDBNK"
    )

    fun buildSelection(): String {
        return bankKeywords.joinToString(
            separator = " OR ",
            prefix = "(",
            postfix = ")"
        ) {
            "address LIKE ?"
        }
    }

    fun buildArgs(): Array<String> {
        return bankKeywords.map { "%$it%" }.toTypedArray()
    }
}
