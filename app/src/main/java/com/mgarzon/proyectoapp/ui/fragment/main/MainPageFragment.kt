package com.mgarzon.proyectoapp.ui.fragment.main

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.mgarzon.proyectoapp.R
import com.mgarzon.proyectoapp.databinding.FragmentMainPageBinding
import com.mgarzon.proyectoapp.firebase.AuthManager
import com.mgarzon.proyectoapp.firebase.FirestoreManager
import com.mgarzon.proyectoapp.model.Review
import com.mgarzon.proyectoapp.model.RecBook
import com.mgarzon.proyectoapp.ui.fragment.detailRecBook.DetailRecBookFragment
import com.mgarzon.proyectoapp.ui.fragment.addedit.AddEditReviewFragment
import com.mgarzon.proyectoapp.ui.fragment.detailReview.DetailReviewFragment


class MainPageFragment : Fragment() {

    private lateinit var binding: FragmentMainPageBinding

    private lateinit var reviewsViewModel: ReviewsViewModel

    private val adapter = ReviewsAdapter(this@MainPageFragment) { review ->
        navigateToReview(review)
    }

    private val adapterRecBook = RecBooksAdapter() { recBook ->
        recBooksViewModel.navigateTo(recBook)
    }

    private val recBooksViewModel: RecBooksViewModel by activityViewModels() {
        MainViewModelFactory(getString(R.string.key))
    }

    //Inflar la vista del fragmento con el menú del activity en visible
    private var mActivity: Activity? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main_page, container, false)
        if (mActivity == null) {
            mActivity = container?.context as? Activity
        }

        val menu = mActivity?.findViewById<CoordinatorLayout>(R.id.coordinatorLayout)
        menu?.visibility = View.VISIBLE
        val background = mActivity?.findViewById<View>(R.id.constrainLayout)
        background?.setBackgroundResource(R.drawable.img_fondo_1)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainPageBinding.bind(view).apply {

            //Bienvenida al usuario registrado
            val auth = AuthManager(requireContext())
            val db = FirestoreManager(requireContext())

            //ViewModel de las reseñas
            reviewsViewModel = ReviewsViewModel(db,auth.getCurrentUser()?.uid.toString())

            val currentUserId = auth.getCurrentUser()?.uid
            val userFromDB = db.getUser(currentUserId.toString()) { user ->
                if (user.image?.startsWith("@drawable") == true || user.image.equals("")) {
                    imgUserDP.setImageResource(R.drawable.img_default_user_dp)
                } else {
                    imgUserDP.let {
                        Glide.with(this@MainPageFragment)
                            .load(user.image)
                            .into(it)
                    }
                }
                tvWellcome.text = "Bienvenido ${user.name}"
            }

            //RecyclerView de la lista de reseñas
            rvList.adapter = adapter
            loadReviews()

            //RecyclerView de las recomendaciones
            rvRecomendation.adapter = adapterRecBook
            loadRecBooks()

            //Filtrar reseñas
            etSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    reviewsViewModel.filterReviews(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }

    private fun loadReviews() {
        reviewsViewModel.reviews.observe(viewLifecycleOwner) { reviews ->
            adapter.reviews = reviews
            adapter.notifyDataSetChanged()
        }
    }

    private fun loadRecBooks() {
        recBooksViewModel.state.observe(viewLifecycleOwner) {state ->
            adapterRecBook.recBooksList = state.recBooks
            adapterRecBook.notifyDataSetChanged()

            state.navigateTo?.let { recBook ->
                navigateToRec(recBook)
            }
        }
    }

    private fun navigateToReview(review: Review) {
        findNavController().navigate(
            R.id.action_mainPageFragment_to_detailReviewFragment,
            bundleOf(DetailReviewFragment.REVIEW to review)
        )
    }

    private fun navigateToRec(recBook: RecBook) {
        findNavController().navigate(
            R.id.action_mainPageFragment_to_recBookDetailFragment,
            bundleOf(DetailRecBookFragment.REC_BOOK to recBook)
        )
        recBooksViewModel.navigateDone()
    }

    fun onDelete(review: Review) {
        reviewsViewModel.deleteReview(review)
        Snackbar.make(requireView(), "Reseña Eliminada", Snackbar.LENGTH_SHORT).show()
        adapter.notifyDataSetChanged()
    }

    fun onEdit(review: Review) {
        findNavController().navigate(
            R.id.action_mainPageFragment_to_addEditFragment,
            bundleOf(AddEditReviewFragment.REVIEW to review)
        )
    }
}

