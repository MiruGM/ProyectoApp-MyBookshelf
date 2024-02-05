package com.mgarzon.proyectoapp.ui.fragment.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mgarzon.proyectoapp.firebase.FirestoreManager
import com.mgarzon.proyectoapp.model.Review
import kotlinx.coroutines.launch

class ReviewsViewModel(val db: FirestoreManager, val userId: String) : ViewModel() {

    private val _reviews = MutableLiveData<List<Review>>()
    val reviews: LiveData<List<Review>> get() = _reviews

    init {
        viewModelScope.launch {
            val reviews = db.getReviewsFlow(userId).collect { reviewsList ->
                _reviews.value = reviewsList
            }
        }
    }
}

