package com.mgarzon.proyectoapp.ui.fragment.addedit

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.mgarzon.proyectoapp.R
import com.mgarzon.proyectoapp.databinding.FragmentAddEditBinding
import com.mgarzon.proyectoapp.firebase.AuthManager
import com.mgarzon.proyectoapp.firebase.FirestoreManager
import com.mgarzon.proyectoapp.model.Review

class AddEditReviewFragment : Fragment() {

    companion object {
        const val POS = "position"
    }

    private val viewModel: AddEditReviewViewModel by activityViewModels()

    //Inflar la vista del fragmento con SIN el menú del activity en visible
    private var mActivity: Activity? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_add_edit, container, false)
        if (mActivity == null) {
            mActivity = container?.context as? Activity
        }

        val menu = mActivity?.findViewById<CoordinatorLayout>(R.id.coordinatorLayout)
        menu?.visibility = View.INVISIBLE
        val background = mActivity?.findViewById<View>(R.id.constrainLayout)
        background?.setBackgroundResource(R.color.light_pink)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val auth = AuthManager(requireContext())
        val db = FirestoreManager(requireContext())

        val binding = FragmentAddEditBinding.bind(view).apply {
            val currentUserUID = auth.getCurrentUser()?.uid

            //Crear una reseña
            btnOk.setOnClickListener {
                val review = Review(
                    null,
                    currentUserUID!!,
                    etTitle.text.toString(),
                    etAuthor.text.toString(),
                    etGenre.text.toString(),
                    etReview.text.toString(),
                    swReadAgain.isChecked,
                    etURLCover.text.toString(),
                    userRating?.rating ?: 0f
                )
                viewModel.addReview(review)
                Snackbar.make(view, "Reseña creada", Snackbar.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            }

            btnBack?.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }

        /*val binding = FragmentAddEditBinding.bind(view).apply {
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


        }*/
    }
}