package com.mgarzon.proyectoapp.firebase

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.mgarzon.proyectoapp.model.Review
import com.mgarzon.proyectoapp.model.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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
    suspend fun addReview(review: Review) {
        firestore.collection(REVIEWS).document(review.id.toString()).set(review).await()
    }

    suspend fun updateReview(review: Review) {
        val reviewRef = review.id?.let {
            firestore.collection(REVIEWS).document(it)
        }
        reviewRef?.set(review)?.await()
    }

    suspend fun deleteReviewById(reviewId: String) {
        firestore.collection(REVIEWS).document(reviewId).delete().await()
    }

    fun getReviewsFlow(userId: String): Flow<List<Review>> = callbackFlow {
        val reviewRef = firestore.collection(REVIEWS)
            .whereEqualTo("userId", userId)
            .orderBy("title")
        Log.d("FManager", reviewRef.toString())
        val subscription = reviewRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("FManager", "Error listening to reviews", error)
                close(error)
                return@addSnapshotListener
            }
            snapshot?.let{ QuerySnapshot ->
                val reviews = mutableListOf<Review>()
                for (document in QuerySnapshot.documents) {
                    val review = document.toObject(Review::class.java)
                    review?.id = document.id
                    review?.let { reviews.add(review) }
                }
                Log.d("Reseñas en FManager", "Emitting reviews: $reviews")
                trySend(reviews).isSuccess
            }
        }
        awaitClose { subscription.remove() }
    }
}