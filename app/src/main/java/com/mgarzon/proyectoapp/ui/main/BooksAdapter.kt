package com.mgarzon.proyectoapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mgarzon.proyectoapp.R
import com.mgarzon.proyectoapp.databinding.ViewBookBinding
import com.mgarzon.proyectoapp.model.Book

class BooksAdapter (
    val context: MainPageFragment,
    val listener: (Book) -> Unit) : RecyclerView.Adapter<BooksAdapter.ViewHolder>() {

    var books: List<Book> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_book, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(books[position])
        holder.binding.title.setOnClickListener { listener(books[position]) }
        holder.binding.btnDelete.setOnClickListener { context.onDelete(position) }
        holder.binding.btnEdit.setOnClickListener { context.onEdit(position) }
    }

    override fun getItemCount(): Int = books.size

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val binding = ViewBookBinding.bind(view)

        fun bind (book : Book) {
            binding.title.text = book.title
        }
    }

}