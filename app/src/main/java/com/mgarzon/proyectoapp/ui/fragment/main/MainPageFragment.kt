package com.mgarzon.proyectoapp.ui.fragment.main

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mgarzon.proyectoapp.R
import com.mgarzon.proyectoapp.databinding.FragmentMainPageBinding
import com.mgarzon.proyectoapp.model.Review
import com.mgarzon.proyectoapp.model.RecBook
import com.mgarzon.proyectoapp.ui.fragment.detailRecBook.DetailRecBookFragment
import com.mgarzon.proyectoapp.ui.fragment.addedit.AddEditFragment
import com.mgarzon.proyectoapp.ui.fragment.detailReview.DetailReviewFragment


class MainPageFragment : Fragment() {

    private lateinit var binding: FragmentMainPageBinding

    private val adapter = ReviewsAdapter(this@MainPageFragment) { review ->
        navigateToReview(review)
    }

    private val adapterRecBook = RecBooksAdapter() { recBook ->
        recBooksViewModel.navigateTo(recBook)
    }

    private val reviewsViewModel: ReviewsViewModel by activityViewModels()

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

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainPageBinding.bind(view).apply {
            //Añadir bienvenida al usuario registrado
            // HACER: Modificar para coger desde la BBDD tabla user
            if (arguments != null) {

                //Incluir la foto de perfil del usuario
                val user = arguments?.getString("username")

                tvWellcome.text = "Bienvenido $user :)"
            }
            imgUserDP?.setOnClickListener {
                //aaaaah
            }

            //RecyclerView de la lista de reseñas
            rvList.adapter = adapter
            if (adapter.itemCount == 0) {
                loadReviews()
            }

            //RecyclerView de las recomendaciones
            rvRecomendation?.adapter = adapterRecBook
            loadRecBooks()
        }
    }

    private fun loadReviews() {
        reviewsViewModel.progressVisible.observe(viewLifecycleOwner) { visible ->
            binding.progressBar?.visibility = if (visible) View.VISIBLE else View.GONE
        }

        reviewsViewModel.reviews.observe(viewLifecycleOwner) { books ->
            adapter.reviews = books
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

    fun onDelete(position: Int) {
        reviewsViewModel.deleteReview(position)
        Toast.makeText(requireContext(), "Reseña Eliminada", Toast.LENGTH_SHORT).show()
        adapter.notifyDataSetChanged()
    }

    fun onEdit(position: Int) {
        findNavController().navigate(
            R.id.action_mainPageFragment_to_addEditFragment,
            bundleOf(AddEditFragment.POS to position)
        )
    }
}

