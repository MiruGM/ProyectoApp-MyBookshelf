package com.mgarzon.proyectoapp.ui.fragment.detailRecBook

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.mgarzon.proyectoapp.R
import com.mgarzon.proyectoapp.databinding.FragmentDetailRecBookBinding
import com.mgarzon.proyectoapp.model.RecBook


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
        background?.setBackgroundResource(R.color.light_pink)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailRecBookBinding.bind(view).apply {
            //Botón para volver a la lista
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
            val listaVacia = emptyList<String>()
            //Cargar el libro seleccionado
            val recBook = arguments?.getParcelable<RecBook>(REC_BOOK).let { recBook ->
                tvTitleT.text = recBook?.title
                tvAuthorC.text = recBook?.author.toString()
                tvGenreC.text = recBook?.genre.toString()
                /*tvPublisherC.text = recBook?.publisher*/

                tvDateC.text = recBook?.publishingDate
                tvPageCountC.text = recBook?.pageCount.toString()

                /*tvSynopsisC.text = recBook?.synopsis*/
                Glide.with(this@DetailRecBookFragment)
                    .load(recBook?.urlCover)
                    .centerCrop()
                    .into(ivCover)
            }
        }
    }
}