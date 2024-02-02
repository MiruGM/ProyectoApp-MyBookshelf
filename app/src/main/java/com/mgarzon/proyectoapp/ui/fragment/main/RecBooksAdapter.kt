package com.mgarzon.proyectoapp.ui.fragment.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mgarzon.proyectoapp.R
import com.mgarzon.proyectoapp.databinding.ViewRecommendedBookBinding
import com.mgarzon.proyectoapp.model.RecBook

class RecBooksAdapter(val listener: (RecBook) -> Unit) :
    RecyclerView.Adapter<RecBooksAdapter.ViewHolder>() {

    var recBooksList : List<RecBook> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate((R.layout.view_recommended_book), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = recBooksList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recBooksList[position])
        holder.binding.recommendedBook.setOnClickListener { listener(recBooksList[position]) }
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ViewRecommendedBookBinding.bind(view)

        fun bind(recbook: RecBook) {
            binding.rbTitle.text = recbook.title
            Glide
                .with(binding.rbImage)
                .load(recbook.urlCover)
                .into(binding.rbImage)
        }
    }

}