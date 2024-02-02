package com.mgarzon.proyectoapp.ui.fragment.detailReview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mgarzon.proyectoapp.model.Review

class DetailViewModel (review: Review): ViewModel() {
    private val _book = MutableLiveData(review)
    val review : LiveData<Review> get() = _book
}

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val review : Review): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(review) as T
    }
}