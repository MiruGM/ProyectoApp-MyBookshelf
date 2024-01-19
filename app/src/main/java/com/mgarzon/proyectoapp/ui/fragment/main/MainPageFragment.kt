package com.mgarzon.proyectoapp.ui.fragment.main

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mgarzon.proyectoapp.R
import com.mgarzon.proyectoapp.databinding.FragmentMainPageBinding
import com.mgarzon.proyectoapp.model.Book
import com.mgarzon.proyectoapp.model.RecBook
import com.mgarzon.proyectoapp.ui.fragment.RecommendedDetailFragment
import com.mgarzon.proyectoapp.ui.fragment.addedit.AddEditFragment
import com.mgarzon.proyectoapp.ui.fragment.detail.DetailFragment


class MainPageFragment : Fragment() {

    private lateinit var binding: FragmentMainPageBinding

    private val adapter = BooksAdapter(this@MainPageFragment) { book ->
        navigateTo(book)
    }

    private val adapterRec = RecommendationsAdapter(bookRecommendations) { recBook ->
        navigateToRec(recBook)
    }

    private val viewModel: MainPageViewModel by activityViewModels()

    private var mActivity: Activity? = null

    /*public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater . inflate (R.layout.fragment_main_page, container, false)

        if (mActivity == null) {
            mActivity = (Activity) container . getContext ();)
        }
        CoordinatorLayout menu =(CoordinatorLayout) mActivity . findViewById (R.id.coordinatorLayout)
        menu.setVisibility(View.VISIBLE)


        return root
    }*/

    //Inflar la vista del fragmento con el menú del activity en visible
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main_page, container, false)
        if (mActivity == null) {
            mActivity = container?.context as? Activity
        }

        val menu = mActivity?.findViewById<CoordinatorLayout>(R.id.coordinatorLayout)
        menu?.visibility = View.VISIBLE

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainPageBinding.bind(view).apply {
            if (arguments != null) {

                //Incluir la foto de perfil del usuario
                val user = arguments?.getString("username")

                tvWellcome.text = "Bienvenido $user :)"
            }
            imgUserDP?.setOnClickListener {
                //aaaaah
            }

            //RecyclerView de la lista de reseñas
            rvList.adapter = adapter
            //loadBooks()
            if (adapter.itemCount == 0) {
                loadBooks()
            }

            //RecyclerView de las recomendaciones
            rvRecomendation?.adapter = adapterRec
        }
    }

    private fun loadBooks() {
        /*viewModel.progressVisible.observe(viewLifecycleOwner) { visible ->
            binding.progressBar.visibility = if (visible) View.VISIBLE else View.GONE
        }*/

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

    private fun navigateToRec(recBook: RecBook) {
        findNavController().navigate(
            R.id.action_mainPageFragment_to_recomendedDetailFragment,
            bundleOf(RecommendedDetailFragment.REC_BOOK to recBook)
        )
    }

    fun onDelete(position: Int) {
        viewModel.deleteBook(position)
        Toast.makeText(requireContext(), "Reseña Eliminada", Toast.LENGTH_SHORT).show()
        adapter.notifyDataSetChanged()
    }

    fun onEdit(position: Int) {
        findNavController().navigate(
            R.id.action_mainPageFragment_to_addEditFragment,
            bundleOf(AddEditFragment.POS to position)
        )
    }

}

private val bookRecommendations = listOf<RecBook>(
    RecBook(
        "El principito",
        "Antoine de Saint-Exupéry",
        "Novela",
        "Editorial1",
        "12/12/12",
        96,
        "El principito es una novela corta y la obra más famosa del escritor y aviador francés Antoine de Saint-Exupéry.",
        "https://images-na.ssl-images-amazon.com/images/I/51Q8n2VpYVL._SX331_BO1,204,203,200_.jpg"
    ),

    RecBook(
        "El señor de los anillos",
        "J. R. R. Tolkien",
        "fantasy",
        "Editorial1",
        "12/12/12",
        96,
        "El señor de los anillos es una novela de fantasía épica escrita por el filólogo y escritor británico J. R. R. Tolkien.",
        "https://medios.lamarmota.es/senor-de-los-anillos.jpeg"
    ),
    RecBook(
        "El Gran Gatsby",
        "F. Scott Fitzgerald",
        "Novela",
        "Editorial1",
        "12/12/12",
        96,
        "El principito es una novela corta y la obra más famosa del escritor y aviador francés Antoine de Saint-Exupéry.",
        "https://www.anagrama-ed.es/uploads/media/portadas/0001/15/b2834bc4ea71357c8b549dfccdd16d611c6586ea.jpeg"
    ),

    RecBook(
        "El señor de los anillos",
        "J. R. R. Tolkien",
        "fantasy",
        "Editorial1",
        "12/12/12",
        96,
        "El señor de los anillos es una novela de fantasía épica escrita por el filólogo y escritor británico J. R. R. Tolkien.",
        "https://imagessl4.casadellibro.com/a/l/t7/44/9788499890944.jpg"
    ),
    RecBook(
        "El principito",
        "Antoine de Saint-Exupéry",
        "Novela",
        "Editorial1",
        "12/12/12",
        96,
        "El principito es una novela corta y la obra más famosa del escritor y aviador francés Antoine de Saint-Exupéry.",
        "https://images-na.ssl-images-amazon.com/images/I/51Q8n2VpYVL._SX331_BO1,204,203,200_.jpg"
    ),

    RecBook(
        "El señor de los anillos",
        "J. R. R. Tolkien",
        "fantasy",
        "Editorial1",
        "12/12/12",
        96,
        "El señor de los anillos es una novela de fantasía épica escrita por el filólogo y escritor británico J. R. R. Tolkien.",
        "https://images-na.ssl-images-amazon.com/images/I/51Q8n2VpYVL._SX331_BO1,204,203,200_.jpg"
    ),
)