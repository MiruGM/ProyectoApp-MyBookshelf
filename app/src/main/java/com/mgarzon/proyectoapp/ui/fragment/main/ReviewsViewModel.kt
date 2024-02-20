package com.mgarzon.proyectoapp.ui.fragment.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mgarzon.proyectoapp.firebase.FirestoreManager
import com.mgarzon.proyectoapp.model.Review
import kotlinx.coroutines.launch

class ReviewsViewModel(val db: FirestoreManager, val userId: String) : ViewModel() {

    private val _originalReviews = mutableListOf<Review>()
    private val _reviews = MutableLiveData<List<Review>>()
    val reviews: LiveData<List<Review>> get() = _reviews

    init {
        viewModelScope.launch {
            val reviews = db.getReviewsFlow(userId).collect { reviewsList ->
                _originalReviews.clear()
                _originalReviews.addAll(reviewsList)
                _reviews.value = reviewsList
            }

        }
    }

    fun deleteReview(review: Review) {
        viewModelScope.launch {
            db.deleteReviewById(review.id!!)
        }
    }

    fun filterReviews(query: String) {
        if (query.isEmpty()) {
            // Si el query está vacío, restaura la lista original
            _reviews.value = _originalReviews.toList()
        } else {
            // Filtrar la lista original basándose en el query
            val listaFiltrada = _originalReviews.filter { review ->
                review.title.contains(query, ignoreCase = true)
            }
            // Actualizar el MutableLiveData con la lista filtrada
            _reviews.value = listaFiltrada
        }
    }
}

