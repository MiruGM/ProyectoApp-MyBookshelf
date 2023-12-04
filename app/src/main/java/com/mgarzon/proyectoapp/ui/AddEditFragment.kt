package com.mgarzon.proyectoapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mgarzon.proyectoapp.R
import com.mgarzon.proyectoapp.databinding.FragmentAddEditBinding
import com.mgarzon.proyectoapp.model.Book
import com.mgarzon.proyectoapp.model.BooksProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddEditFragment : Fragment(R.layout.fragment_add_edit) {

    companion object {
        const val POS = "position"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentAddEditBinding.bind(view).apply {
            val position = arguments?.getInt(POS)
            // Si position es distinto de -1, es que estoy editando
            if (position != -1) {
                tvEditCrear.text = "Editar Libro"
                etTitle.setText(BooksProvider.listOfBooks[position!!].title)
                etAuthor.setText(BooksProvider.listOfBooks[position].author)
                etGenre.setText(BooksProvider.listOfBooks[position].genre)
                etReview.setText(BooksProvider.listOfBooks[position].review)
                etURLCover.setText(BooksProvider.listOfBooks[position].urlCover)
                swReadAgain.isChecked = BooksProvider.listOfBooks[position].readAgain
            }

            btnOk.setOnClickListener {
                //Creo el libro a guardar
                val book = Book (
                    etTitle.text.toString(),
                    etAuthor.text.toString(),
                    etGenre.text.toString(),
                    etReview.text.toString(),
                    swReadAgain.isChecked,
                    etURLCover.text.toString()
                )
                // Guardo el libro nuevo/editado en la lista
                if (position != -1) {
                    GlobalScope.launch(Dispatchers.Main) {
                        val createBook = withContext(Dispatchers.IO) {
                            BooksProvider.editBook(position, book)
                        }
                    }
                } else {
                    GlobalScope.launch(Dispatchers.Main) {
                        val createBook = withContext(Dispatchers.IO) {
                            BooksProvider.addBook(book)
                        }
                    }
                }
                //Añadir a la pila para volver atrás
                parentFragmentManager.popBackStack()
            }

            btnCancel.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }
    }
}