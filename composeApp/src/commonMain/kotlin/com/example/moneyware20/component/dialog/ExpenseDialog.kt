package com.example.moneyware20.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.moneyware20.component.MoneywareTextField
import com.example.presentation.DialogMode
import containerColor
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import primaryColor
import kotlin.time.Clock

@OptIn(FormatStringsInDatetimeFormats::class)
@Composable
fun ExpenseDialog(
    mode: DialogMode,
    expenseName: String,
    expenseAmount: String,
    selectedDate: LocalDate,
    onExpenseNameChange: (String) -> Unit,
    onExpenseAmountChange: (String) -> Unit,
    onDateChange: (LocalDate) -> Unit,
    datePickerState: Boolean,
    setDatePicker: (Boolean) -> Unit,
    onAddClick: () -> Unit,
    onCancelClick: () -> Unit,
    enabled: Boolean
) {


    val titleText = if (mode == DialogMode.ADD) {
        "Add an Expense"
    } else {
        "Edit Expense"
    }

    val confirmButtonText = if (mode == DialogMode.ADD) {
        "Add"
    } else {
        "Update"
    }

    Dialog(onDismissRequest = onCancelClick) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, primaryColor, RoundedCornerShape(20.dp))
                .background(Color.White, RoundedCornerShape(20.dp))
        ) {

            /* ---------- TITLE ---------- */
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        primaryColor,
                        RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                    )
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = titleText,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {

                /* ---------- EXPENSE NAME ---------- */
                MoneywareTextField(
                    text = expenseName,
                    onValueChange = onExpenseNameChange,
                    hint = "Expense name",
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                /* ---------- DATE PICKER ---------- */
                MoneywareTextField(
                    text = selectedDate.toDisplayText(),
                    onValueChange = onExpenseAmountChange,
                    hint = "Select a Date",
                    modifier = Modifier.fillMaxWidth(),
                    enabled = false,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.EditCalendar,
                            contentDescription = "date picker",
                            tint = primaryColor,
                            modifier = Modifier.size(32.dp).clickable { setDatePicker(true) }
                        )
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                /* ---------- AMOUNT ---------- */
                MoneywareTextField(
                    text = expenseAmount,
                    onValueChange = onExpenseAmountChange,
                    hint = "Expense amount",
                    modifier = Modifier.fillMaxWidth(),
                    keyboardType = KeyboardType.Number
                )

                Spacer(modifier = Modifier.height(24.dp))

                /* ---------- ACTIONS ---------- */
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
                ) {

                    Button(
                        onClick = onAddClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primaryColor, contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        enabled = !expenseName.isBlank() && !expenseAmount.isBlank() && enabled
                    ) {
                        Text(confirmButtonText)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = onCancelClick, modifier = Modifier) {
                        Text(text = "Cancel", color = primaryColor)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        if (datePickerState) {
            MoneywareDatePicker(
                setDatePicker = setDatePicker,
                onDateSelected = onDateChange
            )
        }
    }
}

@Composable
fun MoneywareDatePicker(
    setDatePicker: (Boolean) -> Unit,
    onDateSelected: (LocalDate) -> Unit
) {
    val todayMillis = remember {
        Clock.System.now()
            .toEpochMilliseconds()
    }

    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis <= todayMillis
            }
        }
    )

    DatePickerDialog(
        colors = DatePickerDefaults.colors(containerColor = containerColor),
        onDismissRequest = { setDatePicker(false) },
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val date = Instant
                            .fromEpochMilliseconds(millis)
                            .toLocalDateTime(
                                TimeZone.currentSystemDefault()
                            )
                            .date

                        onDateSelected(date)
                    }
                    setDatePicker(false)
                },
                enabled = datePickerState.selectedDateMillis != null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryColor,
                    contentColor = Color.White
                )

            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { setDatePicker(false) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryColor,
                    contentColor = Color.White
                )
            ) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            modifier = Modifier.verticalScroll(rememberScrollState()),
            colors = DatePickerDefaults.colors(
                selectedYearContainerColor = primaryColor,
                selectedDayContainerColor = primaryColor,
                currentYearContentColor = primaryColor,
                todayDateBorderColor = primaryColor,
                containerColor = containerColor,
                todayContentColor = primaryColor
            )
        )
    }
}

@OptIn(FormatStringsInDatetimeFormats::class)
fun LocalDate.toDisplayText(): String {
    val formatter = LocalDate.Format {
        byUnicodePattern("dd-MM-yyyy")
    }
    return formatter.format(this)
}
