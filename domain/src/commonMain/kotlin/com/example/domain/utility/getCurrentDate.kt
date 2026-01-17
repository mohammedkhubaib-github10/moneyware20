package com.example.domain.utility

import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock


private val now = Clock.System.now()
private val localDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
val today: LocalDate = localDateTime.date


val currentMonth: Month = localDateTime.month
val currentYear: Int = localDateTime.year