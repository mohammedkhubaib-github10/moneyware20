package com.example.authentication

expect class GoogleAuthHelper {

    suspend fun signInGoogle(): Result<UserData>
    fun isSignedIn(): Boolean

    fun getCurrentUser(): UserData?
    fun signOut()
}
