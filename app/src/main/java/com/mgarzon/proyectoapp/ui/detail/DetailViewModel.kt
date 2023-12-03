package com.mgarzon.proyectoapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mgarzon.proyectoapp.model.Book

class DetailViewModel (book: Book): ViewModel() {
    private val _book = MutableLiveData(book)
    val book : LiveData<Book> get() = _book
}

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val book : Book): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(book) as T
    }
}