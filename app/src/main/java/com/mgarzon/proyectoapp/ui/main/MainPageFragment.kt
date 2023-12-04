package com.mgarzon.proyectoapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mgarzon.proyectoapp.R
import com.mgarzon.proyectoapp.databinding.FragmentMainPageBinding
import com.mgarzon.proyectoapp.model.Book
import com.mgarzon.proyectoapp.model.BooksProvider
import com.mgarzon.proyectoapp.ui.AddEditFragment
import com.mgarzon.proyectoapp.ui.detail.DetailFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainPageFragment : Fragment(R.layout.fragment_main_page) {

    private lateinit var binding: FragmentMainPageBinding

    private val adapter = BooksAdapter(this@MainPageFragment) { book ->
        navigateTo(book)
    }

    private val viewModel: MainPageViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainPageBinding.bind(view).apply {
            if (arguments != null) {
                val user = arguments?.getString("username")

                tvWellcome.text = "Bienvenido $user :)"
            }
            rvList.adapter = adapter

            loadBooks()


            btnAdd.setOnClickListener {
                findNavController().navigate(
                    R.id.action_mainPageFragment_to_addEditFragment,
                    bundleOf(AddEditFragment.POS to -1)
                )
            }
        }
    }

    private fun loadBooks() {
        viewModel.progressVisible.observe(viewLifecycleOwner) { visible ->
            binding.progressBar.visibility = if (visible) View.VISIBLE else View.GONE
        }

        viewModel.books.observe(viewLifecycleOwner) { books ->
            adapter.books = books
            adapter.notifyDataSetChanged()
        }
    }

    private fun navigateTo(book: Book) {
        findNavController().navigate(
            R.id.action_mainPageFragment_to_detailFragment,
            bundleOf(DetailFragment.BOOK to book)
        )
    }

    fun onDelete(position: Int) {
        viewModel.deleteBook(position)
        adapter.notifyDataSetChanged()

/*        GlobalScope.launch(Dispatchers.Main) {
          val deleteBook = withContext(Dispatchers.IO) {
              BooksProvider.deleteBook(position)
          }
            Toast.makeText(requireContext(), "Libro eliminado", Toast.LENGTH_SHORT).show()
            adapter.notifyDataSetChanged()
        }*/
    }

    fun onEdit(position: Int) {
        findNavController().navigate(
            R.id.action_mainPageFragment_to_addEditFragment,
            bundleOf(AddEditFragment.POS to position)
        )
    }

}