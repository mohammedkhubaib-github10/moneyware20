package com.example.domain.utility

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

val today: LocalDate =
    Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date