package com.example.moneyware20.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.presentation.ui_model.UserUIModel
import com.example.presentation.viewmodel.LoginViewModel
import containerColor
import moneyware20.composeapp.generated.resources.Res
import moneyware20.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource
import primaryColor

@Composable
fun SplashScreen(
    onNavigation: (UserUIModel?) -> Unit,
    viewModel: LoginViewModel
) {
    val uiState by viewModel.loginUiState.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .background(
                    color = primaryColor,
                    shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                )
                .weight(0.75f).fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AppLogo()
        }
        Text(
            text = "Powered by DEV-MK",
            color = Color.DarkGray,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(24.dp)
        )
    }
    onNavigation(uiState.user)
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AppLogo() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .background(
                    color = Color.LightGray,
                    shape = CircleShape
                )
                .size(160.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.logo),
                contentDescription = "logo",
                modifier = Modifier.size(140.dp).clip(CircleShape)
            )
        }
        Text(
            text = "Moneyware",
            color = Color.White,
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold
        )
        ContainedLoadingIndicator(
            modifier = Modifier.padding(24.dp),
            containerColor = containerColor,
            indicatorColor = primaryColor
        )
    }
}
