package com.mgarzon.proyectoapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.mgarzon.proyectoapp.R
import com.mgarzon.proyectoapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentLoginBinding.bind(view).apply {

            btnLogin.setOnClickListener {
                val user = etUser.text.toString()
                val password = etPassword.text.toString()
                if (user.isNotEmpty() && password.isNotEmpty()) {
                    findNavController().navigate(
                        R.id.action_loginFragment_to_mainPageFragment,
                        bundleOf("username" to user)
                    )
                } else {
                    if (user.isEmpty()) {
                            etUser.error = "Ingrese un usuario"
                    } else if (password.isEmpty()) {
                        etPassword.error = "Ingrese una contraseña"
                    }
                }
            }

            btnRegister.setOnClickListener {
                findNavController().navigate(
                    R.id.action_loginFragment_to_registerFragment
                )
            }
        }
    }
}