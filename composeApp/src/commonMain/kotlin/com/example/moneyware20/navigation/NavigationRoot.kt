package com.example.moneyware20.navigation


import androidx.compose.runtime.Composable
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
import com.example.presentation.viewmodel.LoginViewModel
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier
) {
    val backStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Route.Splash::class, Route.Splash.serializer())
                    subclass(Route.Home::class, Route.Home.serializer())
                }
            }
        },
        Route.Splash
    )
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
                        SplashScreen(onNavigation = { name ->
                            backStack.removeLast()
                            if (name != null) backStack.add(Route.Home(name))
                            else backStack.add(Route.Login)
                        })


                    }
                }

                is Route.Login -> {
                    NavEntry(key) {
                        val viewModel: LoginViewModel = koinViewModel()
                        LoginScreen(
                            viewModel = viewModel,
                            onNextClick = {
                                val user: String = viewModel.onNext()
                                backStack.add(Route.Home(user))
                            },
                            onGoogleClick = {
                                val user: String = viewModel.onGoogle()
                                backStack.add(Route.Home(user))
                            })
                    }
                }

                is Route.Home -> {
                    NavEntry(key) {
                        HomeScreen(key.user)
                    }
                }

                else -> error("Unknown NavKey: $key")
            }
        }
    )
}