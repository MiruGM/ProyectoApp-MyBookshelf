package com.mgarzon.proyectoapp.firebase

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.mgarzon.proyectoapp.model.Review
import com.mgarzon.proyectoapp.model.User
import kotlinx.coroutines.tasks.await

class FirestoreManager(context: Context) {
    val firestore = FirebaseFirestore.getInstance()
    val USERS = "users"
    val REVIEWS = "reviews"

    //Manejar Usuarios
    suspend fun saveUser(user: User) {
        firestore.collection(USERS).document(user.id.toString()).set(user).await()
    }

    fun getUser(userId: String, callback: (User) -> Unit) {
        firestore
            .collection(USERS)
            .whereEqualTo("id", userId)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val user = document.toObject(User::class.java)
                    callback(user)
                    return@addOnSuccessListener
                }
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
            }
    }

    //Manejar Reseñas
    /*suspend fun addReview(review: Review) {
        firestore.collection(REVIEWS).document(review.id.toString()).set(review).await()
    }*/
}