package com.mgarzon.proyectoapp.ui.fragment.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mgarzon.proyectoapp.model.Review
import com.mgarzon.proyectoapp.model.ReviewsProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReviewsViewModel() : ViewModel() {

    private val _progressVisible = MutableLiveData(false)
    val progressVisible: LiveData<Boolean> get() = _progressVisible

    private val _reviews = MutableLiveData<List<Review>>(emptyList())
    val reviews: LiveData<List<Review>> get() = _reviews

    init {
        viewModelScope.launch(Dispatchers.Main) {

            _progressVisible.value = true
            _reviews.value = withContext(Dispatchers.IO) {
                ReviewsProvider.getBooks()
            }
            _progressVisible.value = false
        }
    }

    fun deleteReview(position: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                ReviewsProvider.deleteBook(position)
            }
        }
    }

}
