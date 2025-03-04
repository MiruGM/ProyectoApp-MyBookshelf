package com.mgarzon.proyectoapp.ui.fragment.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.mgarzon.proyectoapp.R
import com.mgarzon.proyectoapp.databinding.ViewBookBinding
import com.mgarzon.proyectoapp.model.Review

class ReviewsAdapter (
    val context: MainPageFragment,
    val listener: (Review) -> Unit) : RecyclerView.Adapter<ReviewsAdapter.ViewHolder>() {

    var reviews: List<Review> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_book, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviews[position])
        holder.binding.title.setOnClickListener { listener(reviews[position]) }
        holder.binding.btnMoreOptions.setOnClickListener {
            val popupMenu = PopupMenu(context.requireContext(), it)
            popupMenu.inflate(R.menu.popup_menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_edit -> {
                        context.onEdit(reviews.get(position))
                        true
                    }
                    R.id.action_delete -> {
                        context.onDelete(reviews.get(position))
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }
    }

    override fun getItemCount(): Int = reviews.size

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val binding = ViewBookBinding.bind(view)

        fun bind (review : Review) {
            binding.title.text = review.title

        }
    }


}