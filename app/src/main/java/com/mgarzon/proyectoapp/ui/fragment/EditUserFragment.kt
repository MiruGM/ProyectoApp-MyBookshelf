package com.mgarzon.proyectoapp.ui.fragment


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.mgarzon.proyectoapp.R
import com.mgarzon.proyectoapp.databinding.FragmentEditUserBinding
import com.mgarzon.proyectoapp.firebase.AuthManager
import com.mgarzon.proyectoapp.firebase.FirestoreManager
import com.mgarzon.proyectoapp.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class EditUserFragment : Fragment(R.layout.fragment_edit_user) {

    lateinit var auth: AuthManager
    lateinit var db: FirestoreManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context: Context = requireContext()
        auth = AuthManager(context)
        db = FirestoreManager(context)

        val binding = FragmentEditUserBinding.bind(view).apply {
            //Listas a rellenar
            var friendList = mutableListOf<String>()
            var bookmarkList = mutableListOf<String>()

            //Rellenar con los datos de la bd
            val currentUserId = auth.getCurrentUser()?.uid
            val userFromDB = db.getUser(currentUserId.toString()) { user ->
                etUser.setText(user.username)
                etName.setText(user.name)
                etMail.setText(user.email)
                etPassword.setText(user.password)
                if (user.image?.equals("@drawable/user_default") == true) {
                    etImgUrl.setText("")
                } else {
                    etImgUrl.setText(user.image)
                }

                friendList = user.friendList ?: mutableListOf()
                bookmarkList = user.bookmarkList ?: mutableListOf()
            }

            //Actualizar los datos del usuario
            btnSave.setOnClickListener {
                //Crear el usuario a partir del UID y los campos:

                val newUser = User(
                    id = currentUserId,
                    username = etUser.text.toString().lowercase(),
                    name = etName.text.toString(),
                    email = etMail.text.toString(),
                    password = etPassword.text.toString(),
                    image = etImgUrl.text.toString(),
                    friendList = friendList,
                    bookmarkList = bookmarkList
                )
                //Guardar el usuario en Firestore si el UID no es null
                if (currentUserId != null) {
                    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                        db.saveUser(newUser)
                    }
                } else {
                    Log.d("RegisterActivity", "Error al obtener el UID del usuario")
                }

                Snackbar.make(view, "Usuario actualizado", Snackbar.LENGTH_SHORT).show()

                parentFragmentManager.popBackStack()
            }

            btnCancel.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }
    }
}