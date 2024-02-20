package com.mgarzon.proyectoapp.ui.fragment.addedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mgarzon.proyectoapp.firebase.FirestoreManager
import com.mgarzon.proyectoapp.model.Review
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddEditReviewViewModel(private val db: FirestoreManager) : ViewModel() {

    fun addReview(review: Review) {
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                db.addReview(review)
            }
        }
    }

    fun updateReview(review: Review) {
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                db.updateReview(review)
            }
        }
    }
}