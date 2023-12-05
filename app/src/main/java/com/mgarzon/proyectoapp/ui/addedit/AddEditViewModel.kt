package com.mgarzon.proyectoapp.ui.addedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mgarzon.proyectoapp.model.Book
import com.mgarzon.proyectoapp.model.BooksProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddEditViewModel(): ViewModel() {

    fun addBook(book: Book) {
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                BooksProvider.addBook(book)
            }
        }
    }

    fun editBook(book: Book, position: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                BooksProvider.editBook(position, book)
            }
        }

    }
}