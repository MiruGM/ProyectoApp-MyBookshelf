package com.mgarzon.proyectoapp.ui.fragment.detailRecBook

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.mgarzon.proyectoapp.R
import com.mgarzon.proyectoapp.databinding.FragmentDetailRecBookBinding
import com.mgarzon.proyectoapp.firebase.AuthManager
import com.mgarzon.proyectoapp.firebase.FirestoreManager
import com.mgarzon.proyectoapp.model.RecBook
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailRecBookFragment : Fragment() {

    companion object {
        const val REC_BOOK = "book"
    }

    //Inflar la vista del fragmento con SIN el menú del activity en visible
    private var mActivity: Activity? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_detail_rec_book, container, false)
        if (mActivity == null) {
            mActivity = container?.context as? Activity
        }

        val menu = mActivity?.findViewById<CoordinatorLayout>(R.id.coordinatorLayout)
        menu?.visibility = View.INVISIBLE
        val background = mActivity?.findViewById<View>(R.id.constrainLayout)
        background?.setBackgroundResource(R.color.white)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailRecBookBinding.bind(view).apply {
            val currentUserID = AuthManager(requireContext()).getCurrentUser()?.uid
            val db = FirestoreManager(requireContext())
            val bookID = arguments?.getParcelable<RecBook>(REC_BOOK)?.id

            //Cargar el icono de bookmark correcto
            lifecycleScope.launch(Dispatchers.IO) {
                if (db.isBookmarked(currentUserID!!, bookID!!)) {
                    btnBookmark.setImageResource(R.drawable.icon_full_bookmark_grey)
                } else {
                    btnBookmark.setImageResource(R.drawable.icon_border_bookmark_grey)
                }
            }

            //Botón para volver a la lista
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            btnBookmark.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {

                    if (db.isBookmarked(currentUserID!!, bookID!!)) {
                        db.removeBookmark(currentUserID, bookID)
                        Snackbar.make(view, "Libro eliminado a favoritos", Snackbar.LENGTH_SHORT)
                            .show()
                        btnBookmark.setImageResource(R.drawable.icon_border_bookmark_grey)
                    } else {
                        db.addBookmark(currentUserID, bookID)
                        Snackbar.make(view, "Libro añadido a favoritos", Snackbar.LENGTH_SHORT)
                            .show()
                        btnBookmark.setImageResource(R.drawable.icon_full_bookmark_grey)
                    }
                }
            }

            //Cargar el libro seleccionado
            val recBook = arguments?.getParcelable<RecBook>(REC_BOOK).let { recBook ->
                tvTitleT.text = recBook?.title
                tvAuthorC.text = recBook?.author?.joinToString(separator = ", ") ?: "Anónimo"
                tvGenreC.text = recBook?.genre?.joinToString(separator = ", ") ?: "Sin género"
                tvPublisherC.text = recBook?.publisher ?: "Sin editorial"
                tvDateC.text = recBook?.publishingDate ?: "Fecha desconocida"
                tvPageCountC.text = recBook?.pageCount.toString()
                tvSynopsisC.text = recBook?.synopsis
                Glide.with(this@DetailRecBookFragment)
                    .load(recBook?.urlCover)
                    .centerCrop()
                    .into(ivCover)
            }
        }
    }
}