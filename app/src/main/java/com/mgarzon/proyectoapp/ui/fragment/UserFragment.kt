package com.mgarzon.proyectoapp.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.mgarzon.proyectoapp.R
import com.mgarzon.proyectoapp.databinding.FragmentUserBinding
import com.mgarzon.proyectoapp.firebase.AuthManager
import com.mgarzon.proyectoapp.ui.activity.LoginActivity


class UserFragment : Fragment(R.layout.fragment_user) {
    lateinit var auth: AuthManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context: Context = requireContext()
        auth = AuthManager(context)

        val binding = FragmentUserBinding.bind(view).apply {

            val user = auth.getCurrentUser()

            llLogout.setOnClickListener {
                auth.signOut()
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)

            }
        }
    }
}