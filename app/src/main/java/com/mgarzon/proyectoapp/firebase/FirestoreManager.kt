package com.mgarzon.proyectoapp.firebase

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.mgarzon.proyectoapp.model.User
import kotlinx.coroutines.tasks.await

class FirestoreManager(context: Context) {
    val firestore = FirebaseFirestore.getInstance()
    //val auth = (context.applicationContext as App).auth
    //val userId = auth.getCurrentUser()?.uid
    val USERS = "users"

    suspend fun saveUser(user: User, userId: String){
        user.id = userId
        firestore.collection(USERS).document(user.id.toString()).set(user).await()
    }
}