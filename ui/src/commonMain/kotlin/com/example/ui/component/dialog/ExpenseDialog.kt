package com.example.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern

@OptIn(FormatStringsInDatetimeFormats::class)
@Composable
fun ExpenseDialog(
    expenseName: String,
    expenseAmount: String,
    selectedDate: LocalDate,
    onExpenseNameChange: (String) -> Unit,
    onExpenseAmountChange: (String) -> Unit,
    onDateChange: (LocalDate) -> Unit,
    onAddClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    val primaryColor = Color(0xFF41817C)
    val myDateFormat = LocalDateTime.Format {
        byUnicodePattern("dd-MM-yyyy")
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
                    text = "Add an Expense",
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
                MoneywareDatePicker()

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
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onCancelClick) {
                        Text("Cancel", color = primaryColor)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = onAddClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primaryColor,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Add")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun MoneywareDatePicker() {
    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()
    SnackbarHost(hostState = snackState, Modifier)
    val openDialog = remember { mutableStateOf(true) }
    // TODO demo how to read the selected date from the state.
    if (openDialog.value) {
        val datePickerState = rememberDatePickerState()
        val confirmEnabled = remember {
            derivedStateOf { datePickerState.selectedDateMillis != null }
        }
        DatePickerDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                openDialog.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        snackScope.launch {
                            snackState.showSnackbar(
                                "Selected date timestamp: ${datePickerState.selectedDateMillis}"
                            )
                        }
                    },
                    enabled = confirmEnabled.value,
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { openDialog.value = false }) { Text("Cancel") }
            },
        ) {
            // The verticalScroll will allow scrolling to show the entire month in case there is not
            // enough horizontal space (for example, when in landscape mode).
            // Note that it's still currently recommended to use a DisplayMode.Input at the state in
            // those cases.
            DatePicker(
                state = datePickerState,
                modifier = Modifier.verticalScroll(rememberScrollState()),
            )
        }
    }
}
