package com.mgarzon.proyectoapp.ui.fragment.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.mgarzon.proyectoapp.R
import com.mgarzon.proyectoapp.databinding.FragmentDetailBinding
import com.mgarzon.proyectoapp.model.Book

class DetailFragment : Fragment(R.layout.fragment_detail) {

    companion object {
        const val BOOK = "book"
    }

    private val viewModel: DetailViewModel by viewModels() {
        DetailViewModelFactory(arguments?.getParcelable<Book>(BOOK)!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailBinding.bind(view).apply {
            //Botón para volver a la lista
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            //Cargar el libro seleccionado
            val book = arguments?.getParcelable<Book>("book").let { book ->
                tvTitleT.text = book?.title
                tvAuthorC.text = book?.author
                tvGenreC.text = book?.genre
                tvReviewC.text = book?.review
                if (book?.readAgain.toString() == "false") {
                    tvReadAgainC.text = "NO"
                } else {
                    tvReadAgainC.text = "SI"
                }
                Glide.with(requireContext()).load(book?.urlCover).into(ivCover as ImageView)
                userRating?.rating = book?.rating ?: 0f


                btnMore.setOnClickListener {
                    searchOnline(book?.title)
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