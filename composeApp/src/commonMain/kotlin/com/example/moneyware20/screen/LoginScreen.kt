package com.example.moneyware20.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.moneyware20.component.MoneywareTextField
import com.example.presentation.ui_state.LoginUIState
import com.example.presentation.viewmodel.LoginViewModel
import moneyware20.composeapp.generated.resources.Res
import moneyware20.composeapp.generated.resources.google
import org.jetbrains.compose.resources.painterResource
import primaryColor

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onNextClick: () -> Unit = {},
    onGoogleClick: () -> Unit = {}
) {
    val uiState by viewModel.loginUiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        HeaderSection()

        Spacer(modifier = Modifier.height(32.dp))

        ContentSection(
            onNextClick = onNextClick,
            onGoogleClick = onGoogleClick,
            uiState = uiState,
            viewModel = viewModel
        )
    }
}

@Composable
private fun HeaderSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0x3341817C),
                shape = RoundedCornerShape(bottomEnd = 200.dp)
            )
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Text(
            text = "Let’s get started with Moneyware",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = primaryColor,
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}

@Composable
private fun ContentSection(
    onNextClick: () -> Unit,
    onGoogleClick: () -> Unit,
    uiState: LoginUIState,
    viewModel: LoginViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        MoneywareTextField(
            text = uiState.name,
            onValueChange = { viewModel.onNameChange(it) },
            hint = "Name",
            modifier = Modifier.fillMaxWidth()
        )

        MoneywareTextField(
            text = uiState.phoneNo,
            onValueChange = {
                val filtered = it
                    .filter {
                        it.isDigit()
                    }
                viewModel.onPhoneNoChange(filtered)
            },
            hint = "Phone number",
            keyboardType = KeyboardType.Phone,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = null
                )
            }
        )

        PrimaryButton(
            text = "Next",
            onClick = onNextClick
        )

        OrDivider()

        GoogleSignInButton(
            onClick = onGoogleClick
        )
    }
}

@Composable
private fun PrimaryButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = primaryColor,
            contentColor = Color.White
        )
    ) {
        Text(text = text)
    }
}

@Composable
private fun OrDivider() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f))
        Text(
            text = "Or",
            modifier = Modifier.padding(horizontal = 8.dp),
            style = MaterialTheme.typography.labelMedium
        )
        HorizontalDivider(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun GoogleSignInButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(24.dp)
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Unspecified
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(Res.drawable.google),
                contentDescription = "Google Icon",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "Continue with Google", color = Color.Black)
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
