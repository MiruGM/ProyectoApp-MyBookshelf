package com.mgarzon.proyectoapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.mgarzon.proyectoapp.R
import com.mgarzon.proyectoapp.databinding.FragmentRecommendedDetailBinding
import com.mgarzon.proyectoapp.model.RecBook


class RecommendedDetailFragment : Fragment(R.layout.fragment_recommended_detail) {

    companion object {
        const val REC_BOOK = "book"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentRecommendedDetailBinding.bind(view).apply {
            //Botón para volver a la lista
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            //Cargar el libro seleccionado
            val recBook = arguments?.getParcelable<RecBook>(REC_BOOK).let { recBook ->
                tvTitleT.text = recBook?.title
                tvAuthorC.text = recBook?.author
                tvGenreC.text = recBook?.genre
                tvPublisherC.text = recBook?.publisher
                tvDateC.text = recBook?.publishingDate
                tvPageCountC.text = recBook?.pageCount.toString()
                tvSynopsisC.text = recBook?.synopsis
                Glide.with(this@RecommendedDetailFragment)
                    .load(recBook?.urlCover)
                    .centerCrop()
                    .into(ivCover)

            }
        }
    }
}