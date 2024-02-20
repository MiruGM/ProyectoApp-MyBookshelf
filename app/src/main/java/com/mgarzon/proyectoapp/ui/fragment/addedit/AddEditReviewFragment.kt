package com.mgarzon.proyectoapp.ui.fragment.addedit

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.mgarzon.proyectoapp.R
import com.mgarzon.proyectoapp.databinding.FragmentAddEditBinding
import com.mgarzon.proyectoapp.firebase.AuthManager
import com.mgarzon.proyectoapp.firebase.FirestoreManager
import com.mgarzon.proyectoapp.model.Review
import com.mgarzon.proyectoapp.ui.fragment.main.MainPageFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddEditReviewFragment : Fragment() {

    companion object {
            const val REVIEW = "review"
    }
    private lateinit var viewModel: AddEditReviewViewModel

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

        viewModel = AddEditReviewViewModel(FirestoreManager(requireContext()))
        val auth = AuthManager(requireContext())

        val binding = FragmentAddEditBinding.bind(view).apply {
            val currentUserUID = auth.getCurrentUser()?.uid

            // Si la review no está vacía es que estoy editando
            val oldReview = arguments?.getParcelable<Review>(REVIEW)
            if (oldReview != null) {
                tvEditCrear.text = "Editar Reseña"
                etTitle.setText(oldReview.title)
                etAuthor.setText(oldReview.author)
                etGenre.setText(oldReview.genre)
                etReview.setText(oldReview.review)
                swReadAgain.isChecked = oldReview.readAgain
                etURLCover.setText(oldReview.urlCover)
                userRating?.rating = oldReview.rating
            }

            //Crear una reseña
            btnOk.setOnClickListener {
                val review = Review(
                    id= oldReview?.id ?: "",
                    currentUserUID!!,
                    etTitle.text.toString(),
                    etAuthor.text.toString(),
                    etGenre.text.toString(),
                    etReview.text.toString(),
                    swReadAgain.isChecked,
                    etURLCover.text.toString(),
                    userRating?.rating ?: 0f
                )

                if (oldReview != null) {
                    viewModel.updateReview(review)
                } else {
                    viewModel.addReview(review)
                }

                Snackbar.make(view, "Reseña creada", Snackbar.LENGTH_SHORT).show()

                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, MainPageFragment())
                    .commit()
            }

            btnBack.setOnClickListener {
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, MainPageFragment())
                    .commit()
            }
        }
    }
}