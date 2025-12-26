package com.example.moneyware20.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.example.moneyware20.screen.HomeScreen
import com.example.moneyware20.screen.SplashScreen
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

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
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
        ),
        entryProvider = { key ->
            when(key) {
                is Route.Splash -> {
                    NavEntry(key) {
                        SplashScreen{name, login ->
                            if (login) backStack.add(Route.Home(name))
                        }


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