package com.example.moneyware20.screen

import BudgetDialog
import BudgetDialogMode
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButtonMenu
import androidx.compose.material3.FloatingActionButtonMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleFloatingActionButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moneyware20.component.header.MainHeader
import kotlinx.coroutines.launch
import moneyware20.composeapp.generated.resources.Res
import moneyware20.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource


@Composable
fun HomeScreen(userName: String) {
    val viewModel: BudgetViewModel = viewModel()
    val uiState by viewModel.budgetUIState.collectAsState()
    val dialog by viewModel.dialogState.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var mode by rememberSaveable { mutableStateOf(BudgetDialogMode.ADD) }

    MoneywareDrawer(
        drawerState = drawerState,
        userName = "Mohammed Khubaib C",
        profileImage = painterResource(Res.drawable.logo),
        onSettingsClick = { /* navigate */ },
        onSignOutClick = { /* sign out */ }
    ) {
        Scaffold(
            topBar = {
                MainHeader(onActionIconClick = {}, onNavigationIconClick = {
                    scope.launch {
                        drawerState.open()
                    }
                })
            },
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = {
                Fab(viewModel)
            }
        ) { it ->
        }
    }
    if (dialog) {
        BudgetDialog(
            mode = mode,
            budgetName = uiState.budgetName,
            budgetAmount = uiState.budgetAmount,
            selectedType = uiState.budgetType,
            onBudgetNameChange = { viewModel.onBudgetNameChange(it) },
            onBudgetAmountChange = { viewModel.onBudgetAmountChange(it) },
            onBudgetTypeChange = { viewModel.onBudgetTypeChange(it) },
            onConfirmClick = {
                viewModel.onAddBudget()
                viewModel.toggleDialog(false)
            },
            onCancelClick = {
                viewModel.onCancel()
                viewModel.toggleDialog(false)
            },
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Fab(viewModel: BudgetViewModel) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val animatedColor by animateColorAsState(
        targetValue = if (expanded) Color(0xFF41817C) else Color(0xFF2C6B65)
    )
    FloatingActionButtonMenu(
        expanded = expanded,
        button = {
            ToggleFloatingActionButton(
                containerColor = { animatedColor },
                checked = expanded,
                onCheckedChange = { expanded = !expanded }
            ) {
                val icon = if (expanded) Icons.Filled.Close else Icons.Filled.Add
                Icon(icon, contentDescription = null, tint = Color.White)
            }
        }
    ) {
        // Manual Entry
        FloatingActionButtonMenuItem(
            onClick = { expanded = false; viewModel.toggleDialog(true) },
            icon = {
                Icon(
                    Icons.Filled.Edit,
                    contentDescription = "Manual Entry",
                    tint = Color.White
                )
            },
            text = { Text("Manual Entry", color = Color.White) },
            containerColor = Color(0xFF41817C)
        )
    }
}

@Composable
fun MoneywareDrawer(
    drawerState: DrawerState,
    userName: String,
    profileImage: Painter,
    onSettingsClick: () -> Unit,
    onSignOutClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val primaryColor = Color(0xFF41817C)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(300.dp),
                drawerContainerColor = Color.White,
                windowInsets = WindowInsets(0),
            ) {
                /* ---------- HEADER ---------- */
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .background(
                            primaryColor,
                            shape = RoundedCornerShape(
                                bottomStart = 32.dp,
                                bottomEnd = 32.dp
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Profile",
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }

                /* ---------- PROFILE IMAGE ---------- */
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-40).dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = profileImage,
                            contentDescription = null,
                            modifier = Modifier
                                .size(96.dp)
                                .clip(CircleShape)
                                .border(2.dp, Color.White, CircleShape)
                        )
                        Text(
                            text = userName,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalDivider()

                    }
                }
                /* ---------- NAME ---------- */


                /* ---------- SETTINGS ---------- */
                DrawerItem(
                    icon = Icons.Default.Settings,
                    title = "Settings",
                    onClick = onSettingsClick
                )

                /* ---------- SIGN OUT ---------- */
                DrawerItem(
                    icon = Icons.AutoMirrored.Filled.Logout,
                    title = "Sign Out",
                    onClick = onSignOutClick
                )
            }
        },
        content = content
    )
}


@Composable
private fun DrawerItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
