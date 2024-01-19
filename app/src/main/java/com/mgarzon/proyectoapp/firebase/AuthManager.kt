package com.mgarzon.proyectoapp.firebase

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth
import kotlinx.coroutines.tasks.await

class AuthManager(context: Context) {

    private val firebaseAuth: FirebaseAuth by lazy { Firebase.auth }

    private val googleSignInClient: GoogleSignInClient by lazy {
        GoogleSignIn.getClient(context, GoogleSignInOptions.DEFAULT_SIGN_IN)
    }

    suspend fun createUserWithEmailAndPassword(email: String, password: String):
            AuthRes<FirebaseUser?> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            AuthRes.Success(authResult.user)
        } catch (e: Exception) {
            AuthRes.Error(e.message ?: "Error al crear el usuario")
        }
    }

    suspend fun signInWithEmailAndPassword(email: String, password: String):
            AuthRes<FirebaseUser?> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            AuthRes.Success(authResult.user)
        } catch (e: Exception) {
            AuthRes.Error(e.message ?: "Error al iniciar sesión")
        }
    }

    suspend fun resetPassword(email: String): AuthRes<Unit> {
        return try {
            val authResult = firebaseAuth.sendPasswordResetEmail(email).await()
            AuthRes.Success(Unit)
        } catch (e: Exception) {
            AuthRes.Error(e.message ?: "Error al enviar el correo")
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
        //googleSignInClient.signOut()
    }

    fun getCurrentUser() = firebaseAuth.currentUser

    fun handleSignInResult(task : Task<GoogleSignInAccount>):
            AuthRes<GoogleSignInAccount?> {
        return try {
            val account = task.getResult(ApiException::class.java)
            AuthRes.Success(account)
        } catch (e: Exception) {
            AuthRes.Error(e.message ?: "Error al iniciar sesión")
        }
    }

    suspend fun googleSignInCredential(credential: AuthCredential):
            AuthRes<FirebaseUser?> {
        return try {
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            AuthRes.Success(authResult.user)
        } catch (e: Exception) {
            AuthRes.Error(e.message ?: "Error al iniciar sesión")
        }
    }

    fun signInWithGoogle(googleSignLauncher: ActivityResultLauncher<Intent>) {
        val signInIntent = googleSignInClient.signInIntent
        googleSignLauncher.launch(signInIntent)
    }

}

sealed class AuthRes<out T> {
    data class Success<T>(val data: T) : AuthRes<T>()
    data class Error(val errorMensaje: String) : AuthRes<Nothing>()
}