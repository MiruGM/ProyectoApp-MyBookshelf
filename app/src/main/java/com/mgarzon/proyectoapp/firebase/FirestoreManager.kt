package com.mgarzon.proyectoapp.firebase

import android.content.Context
import com.google.firebase.firestore.FieldValue
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

    suspend fun getUserByUsername (username: String): User? {
        val userRef = firestore
            .collection(USERS)
            .whereEqualTo("username", username)
            .get()
            .await()
        for (document in userRef.documents) {
            val user = document.toObject(User::class.java)
            return user
        }
        return null
    }

    suspend fun deleteUser(userId: String) {
        firestore.collection(USERS).document(userId).delete().await()
    }

    //Añadir y eliminar libros a la lista de favoritos
    suspend fun addBookmark(userId: String, bookId: String) {
        val userRef = firestore.collection(USERS).document(userId)
        userRef.update("bookmarkList", FieldValue.arrayUnion(bookId)).await()
    }

    suspend fun removeBookmark(userId: String, bookId: String) {
        val userRef = firestore.collection(USERS).document(userId)
        userRef.update("bookmarkList", FieldValue.arrayRemove(bookId)).await()
    }

    suspend fun isBookmarked(userId: String, bookId: String): Boolean {
        val userRef = firestore.collection(USERS).document(userId)
        val user = userRef.get().await().toObject(User::class.java)
        return user?.bookmarkList?.contains(bookId) ?: false
    }

    suspend fun getBookmarkList(userId: String): List<String> {
        val userRef = firestore.collection(USERS).document(userId)
        val user = userRef.get().await().toObject(User::class.java)
        return user?.bookmarkList ?: emptyList()
    }

    //Manejar Reseñas
    suspend fun addReview(review: Review) {
        firestore.collection(REVIEWS).add(review).await()
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

        val subscription = reviewRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
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
                trySend(reviews).isSuccess
            }
        }
        awaitClose { subscription.remove() }
    }
}