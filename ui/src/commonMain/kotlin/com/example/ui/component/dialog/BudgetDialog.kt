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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.domain.entity.BudgetType
import com.example.ui.component.MoneywareTextField


@Composable
fun BudgetDialog(
    mode: BudgetDialogMode,
    budgetName: String,
    budgetAmount: String,
    selectedType: BudgetType,
    onBudgetNameChange: (String) -> Unit,
    onBudgetAmountChange: (String) -> Unit,
    onBudgetTypeChange: (BudgetType) -> Unit,
    onConfirmClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    val primaryColor = Color(0xFF41817C)

    val titleText = if (mode == BudgetDialogMode.ADD) {
        "Add a Budget"
    } else {
        "Edit Budget"
    }

    val confirmButtonText = if (mode == BudgetDialogMode.ADD) {
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
            Title(titleText, primaryColor)

            Spacer(modifier = Modifier.height(16.dp))

            SelectBudgetType(selectedType, onBudgetTypeChange)

            Spacer(modifier = Modifier.height(16.dp))

            /* ---------- NAME ---------- */
            MoneywareTextField(
                text = budgetName,
                onValueChange = onBudgetNameChange,
                hint = "Budget name",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            /* ---------- AMOUNT ---------- */
            MoneywareTextField(
                text = budgetAmount,
                onValueChange = onBudgetAmountChange,
                hint = "Budget amount",
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
                    Text(text = "Cancel", color = primaryColor)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = onConfirmClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryColor,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(confirmButtonText)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun Title(titleText: String, primaryColor: Color) {
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
}

@Composable
fun SelectBudgetType(selectedType: BudgetType, onBudgetTypeChange: (BudgetType) -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {

        /* ---------- BUDGET TYPE ---------- */
        Text(
            text = "Select budget type",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selectedType == BudgetType.AUTOMATIC,
                onClick = { onBudgetTypeChange(BudgetType.AUTOMATIC) }
            )
            Text("Automatic")

            Spacer(modifier = Modifier.width(16.dp))

            RadioButton(
                selected = selectedType == BudgetType.MANUAL,
                onClick = { onBudgetTypeChange(BudgetType.MANUAL) }
            )
            Text("Manual")
        }
    }
}

enum class BudgetDialogMode {
    ADD,
    EDIT
}


