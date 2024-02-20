package com.mgarzon.proyectoapp.ui.fragment

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.mgarzon.proyectoapp.R
import com.mgarzon.proyectoapp.databinding.FragmentAddFriendBinding
import com.mgarzon.proyectoapp.firebase.AuthManager
import com.mgarzon.proyectoapp.firebase.FirestoreManager
import com.mgarzon.proyectoapp.ui.fragment.main.MainPageFragment
import kotlinx.coroutines.launch

class AddFriendFragment : Fragment() {

    //Inflar la vista del fragmento con SIN el menú del activity en visible
    private var mActivity: Activity? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_add_friend, container, false)
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

        val auth = AuthManager(requireContext())
        val db = FirestoreManager(requireContext())

        val binding = FragmentAddFriendBinding.bind(view).apply {

            btnSearch.setOnClickListener {
                //Buscar usuario por username
                val username = etSearch.text.toString().lowercase()
                viewLifecycleOwner.lifecycleScope.launch {
                    val currentUser = auth.getCurrentUser()
                    val resultUser = db.getUserByUsername(username)
                    if (resultUser == null) {
                        tvNotFound.visibility = View.VISIBLE
                        llResultUser.visibility = View.GONE
                    } else {
                        tvNotFound.visibility = View.GONE
                        //Carga de datos
                        if (resultUser.image?.startsWith("@drawable") == true || resultUser.image.equals(
                                ""
                            )
                        ) {
                            imgResultUserDP.setImageResource(R.drawable.img_default_user_dp)
                        } else {
                            imgResultUserDP.let {
                                Glide.with(this@AddFriendFragment)
                                    .load(resultUser.image)
                                    .into(it)
                            }
                        }
                        tvResultName.text = resultUser.name
                        tvResultUsername.text = "@${resultUser.username}"

                        //Comprobar si son amigos para mostrar el botón de añadir/eliminar amigo
                        if (resultUser.friendList!!.contains(currentUser!!.uid)) {
                            btnDeleteFriend.visibility = View.VISIBLE
                            btnAddFriend.visibility = View.GONE
                        } else {
                            btnDeleteFriend.visibility = View.GONE
                            btnAddFriend.visibility = View.VISIBLE
                        }

                        //Muestro el elemento
                        llResultUser.visibility = View.VISIBLE

                        //Botón de añadir amigo
                        btnAddFriend.setOnClickListener {
                            //Modal de confirmación
                            AlertDialog.Builder(requireContext())
                                .setTitle("Añadir amigo")
                                .setMessage("¿Seguro de que quieres añadir a @${resultUser.username} como amigo?")
                                .setPositiveButton("Sí") { dialog, id ->
                                    //Añadir amigo

                                    //Actualizar botones
                                    btnDeleteFriend.visibility = View.VISIBLE
                                    btnAddFriend.visibility = View.GONE
                                    //Snackbar
                                    Snackbar.make(view, "Amigo añadido", Snackbar.LENGTH_SHORT)
                                        .show()
                                }
                                .setNegativeButton("No") { dialog, id ->
                                    dialog.cancel()
                                }
                                .create()
                                .show()
                        }
                    }
                }
            }
            btnBack.setOnClickListener {
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, MainPageFragment())
                    .commit()
            }
        }

    }
}