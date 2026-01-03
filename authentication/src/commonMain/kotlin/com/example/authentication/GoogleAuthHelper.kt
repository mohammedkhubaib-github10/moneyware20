package com.example.authentication

expect class GoogleAuthHelper {

    suspend fun signInGoogle(): Result<UserData>

    fun signOut()
}
