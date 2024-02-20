package com.mgarzon.proyectoapp.ui.fragment.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mgarzon.proyectoapp.model.RecBook
import com.mgarzon.proyectoapp.model.server.RemoteConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecBooksViewModel (val apiKey: String) : ViewModel() {

    private val _state = MutableLiveData<UiState>(UiState())
    val state: LiveData<UiState> get() = _state

    init {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val result = RemoteConnection.service.listOfRecBooks(apiKey)

                if (result != null && result.items != null) {
                    val recBooksList = result.items.map { item ->
                        RecBook(
                            item.id,
                            item.volumeInfo.title,
                            item.volumeInfo.authors,
                            item.volumeInfo.categories,
                            item.volumeInfo.publisher,
                            item.volumeInfo.publishedDate,
                            item.volumeInfo.pageCount,
                            item.volumeInfo.description,
                            convertToHttps(item.volumeInfo.imageLinks.thumbnail)
                        )
                    }
                    _state.value = _state.value?.copy(recBooks = recBooksList)
                } else {
                    Log.d("Error: ", "No se ha podido obtener la lista de libros")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun convertToHttps(url: String): String {
        return if (url.startsWith("http://")) {
            "https://${url.substring(7)}"
        } else {
            url
        }
    }

    fun navigateTo(recBook: RecBook) {
        _state.value = _state.value?.copy(navigateTo = recBook)
    }

    fun navigateDone() {
        _state.value = _state.value?.copy(navigateTo = null)
    }
}

data class UiState(
    val recBooks: List<RecBook> = emptyList(),
    val navigateTo: RecBook? = null
)

class MainViewModelFactory(private val apiKey: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RecBooksViewModel(apiKey) as T
    }
}