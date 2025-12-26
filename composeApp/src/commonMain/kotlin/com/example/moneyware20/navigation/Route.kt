package com.example.moneyware20.navigation


import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route: NavKey {

    @Serializable
    data object Splash: Route, NavKey

    @Serializable
    data class Home(val user: String): Route, NavKey
}