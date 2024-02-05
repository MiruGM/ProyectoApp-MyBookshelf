package com.mgarzon.proyectoapp.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import com.bumptech.glide.Glide
import com.mgarzon.proyectoapp.R
import com.mgarzon.proyectoapp.databinding.FragmentUserBinding
import com.mgarzon.proyectoapp.firebase.AuthManager
import com.mgarzon.proyectoapp.firebase.FirestoreManager
import com.mgarzon.proyectoapp.ui.activity.LoginActivity


class UserFragment : Fragment(R.layout.fragment_user) {

    lateinit var auth: AuthManager
    lateinit var db: FirestoreManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context: Context = requireContext()
        auth = AuthManager(context)
        db = FirestoreManager(context)

        val binding = FragmentUserBinding.bind(view).apply {
            //Listas a rellenar
            var friendList = mutableListOf<String>()
            var bookmarkList = mutableListOf<String>()

            //Rellenar con los datos de la bd
            val currentUserId = auth.getCurrentUser()?.uid
            val userFromDB = db.getUser(currentUserId.toString()) { user ->
                if (user.image?.startsWith("@drawable") == true || user.image.equals("")) {
                    Log.d("Foto de perfil: ", "No hay foto de perfil")
                } else {
                    Glide.with(this@UserFragment)
                        .load(user.image)
                        .into(ivUserDP)
                    tvName.setText(user.name)
                    tvUser.setText(user.username)
                }

                friendList = user.friendList ?: mutableListOf()
                bookmarkList = user.bookmarkList ?: mutableListOf()
            }

            llEditProfile.setOnClickListener {
                val fragment = EditUserFragment()
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit()
            }

            ivOpenFriendsList.setOnClickListener {
                expandableContentFriends.visibility = if (expandableContentFriends.visibility == View.GONE) View.VISIBLE else View.GONE
                //rvFriendList.adapter = FriendListAdapter(friendList)
            }

            ivOpenBookmarkList.setOnClickListener {
                expandableContentBookmark.visibility = if (expandableContentBookmark.visibility == View.GONE) View.VISIBLE else View.GONE
                //rvBookmarkList.adapter = BookmarkListAdapter(bookmarkList)
            }

            llLogout.setOnClickListener {
                auth.signOut()
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)

            }
        }
    }
}