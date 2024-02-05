package com.mgarzon.proyectoapp.ui.fragment.addedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mgarzon.proyectoapp.firebase.FirestoreManager
import com.mgarzon.proyectoapp.model.Review
import com.mgarzon.proyectoapp.model.ReviewsProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddEditReviewViewModel(val db: FirestoreManager): ViewModel() {

    fun addReview(review: Review) {
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                db.addReview(review)
                //ReviewsProvider.addBook(review)
            }
        }
    }

    fun editBook(review: Review, position: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                //ReviewsProvider.editBook(position, review)
            }
        }

    }
}