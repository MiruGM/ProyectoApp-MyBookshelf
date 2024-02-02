package com.mgarzon.proyectoapp.ui.fragment.addedit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.mgarzon.proyectoapp.R
import com.mgarzon.proyectoapp.databinding.FragmentAddEditBinding
import com.mgarzon.proyectoapp.model.Review
import com.mgarzon.proyectoapp.model.ReviewsProvider

class AddEditFragment : Fragment(R.layout.fragment_add_edit) {

    companion object {
        const val POS = "position"
    }

    private val viewModel: AddEditViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val binding = FragmentAddEditBinding.bind(view).apply {
            val position = arguments?.getInt(POS)
            // Si position es distinto de -1, es que estoy editando
            if (position != -1) {
                tvEditCrear.text = "Editar Libro"
                etTitle.setText(ReviewsProvider.listOfReviews[position!!].title)
                etAuthor.setText(ReviewsProvider.listOfReviews[position].author)
                etGenre.setText(ReviewsProvider.listOfReviews[position].genre)
                etReview.setText(ReviewsProvider.listOfReviews[position].review)
                etURLCover.setText(ReviewsProvider.listOfReviews[position].urlCover)
                swReadAgain.isChecked = ReviewsProvider.listOfReviews[position].readAgain
                userRating?.rating = ReviewsProvider.listOfReviews[position].rating
            }

            btnOk.setOnClickListener {
                //Creo el libro a guardar
                val review = Review(
                    etTitle.text.toString(),
                    etAuthor.text.toString(),
                    etGenre.text.toString(),
                    etReview.text.toString(),
                    swReadAgain.isChecked,
                    etURLCover.text.toString(),
                    userRating?.rating ?: 0f
                )

                // Guardo el libro nuevo/editado en la lista
                if (position != -1) {
                    viewModel.editBook(review, position)
                } else {
                    viewModel.addBook(review)
                }
                //Añadir a la pila para volver atrás
                parentFragmentManager.popBackStack()
            }

            btnBack?.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }
    }
}