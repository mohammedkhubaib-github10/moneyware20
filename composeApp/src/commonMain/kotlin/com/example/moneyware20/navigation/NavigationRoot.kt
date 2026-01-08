package com.example.moneyware20.navigation


import Route
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.example.moneyware20.screen.LoginScreen
import com.example.moneyware20.screen.SplashScreen
import com.example.moneyware20.screen.homescreen.HomeScreen
import com.example.presentation.AuthState
import com.example.presentation.viewmodel.BudgetViewModel
import com.example.presentation.viewmodel.LoginViewModel
import com.example.presentation.viewmodel.SplashViewModel
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.compose.viewmodel.koinViewModel
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier
) {

    val backStack = rememberNavBackStack(configuration = SavedStateConfiguration {
        serializersModule = SerializersModule {
            polymorphic(NavKey::class) {
                subclass(
                    Route.Splash::class,
                    Route.Splash.serializer()
                )
                subclass(Route.Login::class, Route.Login.serializer())
                subclass(Route.Home::class, Route.Home.serializer())
            }
        }
    }, Route.Splash)
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
        ),
        entryProvider = { key ->
            when (key) {
                is Route.Splash -> {
                    NavEntry(key) {
                        val viewModel: SplashViewModel = koinViewModel()
                        val authState: AuthState = getKoin().get()
                        val user by authState.user.collectAsState()
                        LaunchedEffect(user) {
                            backStack.removeLast()
                            println(user.toString())
                            if (user != null) {
                                backStack.add(Route.Home(user!!))
                            } else {
                                backStack.add(Route.Login)
                            }
                        }
                        SplashScreen()
                    }
                }

                is Route.Login -> {
                    NavEntry(key) {
                        val viewModel: LoginViewModel = koinViewModel()
                        val authState: AuthState = getKoin().get()
                        val user by authState.user.collectAsState()
                        LaunchedEffect(user) {
                            println(user.toString() + " login")
                            user?.let { user ->
                                backStack.removeLast()
                                backStack.add(Route.Home(user))
                            }
                        }
                        LoginScreen(
                            viewModel = viewModel
                        )
                    }
                }

                is Route.Home -> {
                    NavEntry(key) {
                        val viewModel: BudgetViewModel = koinViewModel()
                        val authState: AuthState = getKoin().get()
                        val user by authState.user.collectAsState()
                        LaunchedEffect(user) {
                            println(user.toString() + "  Home")
                            if (user == null) {
                                backStack.clear()
                                backStack.add(Route.Login)
                            } else {
                                viewModel.getBudgets()
                            }
                        }
                        HomeScreen(
                            viewModel = viewModel,
                            user = key.user
                        )
                    }
                }

                else -> error("Unknown NavKey: $key")
            }
        }
    )
}