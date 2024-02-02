package com.mgarzon.proyectoapp.ui.fragment.detailReview

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.mgarzon.proyectoapp.R
import com.mgarzon.proyectoapp.databinding.FragmentDetailBinding
import com.mgarzon.proyectoapp.model.Review

class DetailReviewFragment : Fragment() {

    companion object {
        const val REVIEW = "review"
    }

    private val viewModel: DetailViewModel by viewModels() {
        DetailViewModelFactory(arguments?.getParcelable<Review>(REVIEW)!!)
    }

    //Inflar la vista del fragmento con SIN el menú del activity en visible
    private var mActivity: Activity? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_detail, container, false)
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

        val binding = FragmentDetailBinding.bind(view).apply {
            //Botón para volver a la lista
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            //Cargar el libro seleccionado
            val review = arguments?.getParcelable<Review>("review").let { review ->
                tvTitleT.text = review?.title
                tvAuthorC.text = review?.author
                tvGenreC.text = review?.genre
                tvReviewC.text = review?.review
                if (review?.readAgain.toString() == "false") {
                    tvReadAgainC.text = "NO"
                } else {
                    tvReadAgainC.text = "SI"
                }
                Glide.with(requireContext()).load(review?.urlCover).into(ivCover as ImageView)
                userRating?.rating = review?.rating ?: 0f


                btnMore.setOnClickListener {
                    searchOnline(review?.title)
                }

                btnFav?.setOnClickListener {

                }

            }
        }
    }
}

private fun searchOnline(title: String?) {
    val webpage: Uri = Uri.parse("https://www.google.com/search?q=$title")
    val intent = Intent(Intent.ACTION_VIEW, webpage)
    //startActivity(intent)
}