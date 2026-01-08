package com.example.authentication

import android.content.Context
import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

actual class GoogleAuthHelper(
    private val context: Context,
    private val firebaseAuth: FirebaseAuth = Firebase.auth
) {

    companion object {
        private const val TAG = "GoogleAuthHelper"
        private const val WEB_CLIENT_ID =
            "426792147218-ukbpmafmsfk2c35dnk1apqpb7ld45qh0.apps.googleusercontent.com"
    }

    actual suspend fun signInGoogle(): Result<UserData> = try {
        val credentialManager = CredentialManager.create(context)

        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(WEB_CLIENT_ID)
            .setFilterByAuthorizedAccounts(false)
            .setAutoSelectEnabled(true)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val result = credentialManager.getCredential(context, request)
        handleSignIn(result.credential)

    } catch (e: Exception) {
        Log.e(TAG, "Google sign-in failed", e)
        Result.failure(e)
    }

    private suspend fun handleSignIn(
        credential: Credential?
    ): Result<UserData> {

        return if (
            credential is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) {
            val googleIdTokenCredential =
                GoogleIdTokenCredential.createFrom(credential.data)

            firebaseAuthWithGoogle(googleIdTokenCredential.idToken)

        } else {
            Result.failure(Exception("Invalid Google credential"))
        }
    }

    private suspend fun firebaseAuthWithGoogle(
        idToken: String
    ): Result<UserData> =
        suspendCancellableCoroutine { cont ->

            val credential =
                GoogleAuthProvider.getCredential(idToken, null)

            firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = task.result.user
                        if (user != null) {
                            cont.resume(
                                Result.success(
                                    UserData(
                                        userId = user.uid,
                                        username = user.displayName,
                                        profilePictureUrl = user.photoUrl?.toString()
                                    )
                                )
                            )
                        } else {
                            cont.resume(
                                Result.failure(Exception("User is null"))
                            )
                        }
                    } else {
                        cont.resume(
                            Result.failure(
                                task.exception
                                    ?: Exception("Sign-in failed")
                            )
                        )
                    }
                }
                .addOnCanceledListener {
                    cont.resume(
                        Result.failure(Exception("Sign-in cancelled"))
                    )
                }
        }

    actual fun isSignedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    actual fun getCurrentUser(): UserData? {
        val user = firebaseAuth.currentUser ?: return null
        return UserData(
            userId = user.uid,
            username = user.displayName,
            profilePictureUrl = user.photoUrl?.toString()
        )
    }

    actual fun signOut() {
        firebaseAuth.signOut()
    }
}
