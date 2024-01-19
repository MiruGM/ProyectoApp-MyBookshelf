package com.mgarzon.proyectoapp.ui.fragment.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mgarzon.proyectoapp.model.Book
import com.mgarzon.proyectoapp.model.BooksProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainPageViewModel() : ViewModel() {
    private val _progressVisible = MutableLiveData(false)
    val progressVisible: LiveData<Boolean> get() = _progressVisible

    private val _books = MutableLiveData<List<Book>>(emptyList())
    val books: LiveData<List<Book>> get() = _books

    init {
        viewModelScope.launch(Dispatchers.Main) {
            _progressVisible.value = true
            _books.value = withContext(Dispatchers.IO) {
                BooksProvider.getBooks()
            }
            _progressVisible.value = false
        }
    }

    fun deleteBook(position: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                BooksProvider.deleteBook(position)
            }
        }
    }

}