package com.example.moneyware20.screen.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.example.moneyware20.component.DrawerItem
import primaryColor

@Composable
fun MoneywareDrawer(
    drawerState: DrawerState,
    userName: String,
    profileImage: Painter,
    onSettingsClick: () -> Unit,
    onSignOutClick: () -> Unit,
    content: @Composable () -> Unit
) {

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

