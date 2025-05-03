// FirebaseAuthDataSourceImpl.kt
package com.gulsenurgunes.furfriends.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.gulsenurgunes.furfriends.data.datasource.AuthDataSource
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FirebaseAuthDataSourceImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthDataSource {

    override suspend fun createUser(
        name: String,
        email: String,
        password: String
    ): FirebaseUser =
        suspendCancellableCoroutine { cont ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                    result.user?.let { cont.resume(it) }
                        ?: cont.resumeWithException(Exception("User null"))
                }
                .addOnFailureListener { exc ->
                    cont.resumeWithException(exc)
                }
            cont.invokeOnCancellation { }
        }

    override suspend fun signIn(
        email: String,
        password: String
    ): FirebaseUser =
        suspendCancellableCoroutine { cont ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                    result.user?.let { cont.resume(it) }
                        ?: cont.resumeWithException(Exception("User null"))
                }
                .addOnFailureListener { exc ->
                    cont.resumeWithException(exc)
                }
            cont.invokeOnCancellation {}
        }

    override suspend fun signInWithGoogle(idToken: String): FirebaseUser =
        suspendCancellableCoroutine { cont ->
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential)
                .addOnSuccessListener { result ->
                    result.user?.let { cont.resume(it) }
                        ?: cont.resumeWithException(Exception("User null"))
                }
                .addOnFailureListener { exc ->
                    Log.e("SignIn", "Google sign-in exception", exc)
                    cont.resumeWithException(exc)
                }
            cont.invokeOnCancellation {}
        }

}
