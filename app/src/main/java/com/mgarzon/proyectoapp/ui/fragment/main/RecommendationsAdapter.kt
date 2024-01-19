package com.mgarzon.proyectoapp.ui.fragment.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mgarzon.proyectoapp.R
import com.mgarzon.proyectoapp.databinding.ViewRecommendedBookBinding
import com.mgarzon.proyectoapp.model.RecBook

class RecommendationsAdapter(
    val bookRecommendations: List<RecBook>,
    val listener: (RecBook) -> Unit
) : RecyclerView.Adapter<RecommendationsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ViewRecommendedBookBinding.bind(view)

        fun bind(recbook: RecBook) {
            binding.rbTitle.text = recbook.title
            Glide.with(binding.rbImage).load(recbook.urlCover).into(binding.rbImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate((R.layout.view_recommended_book), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = bookRecommendations.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bookRecommendations[position])
        holder.binding.recommendedBook.setOnClickListener { listener(bookRecommendations[position]) }
    }
}