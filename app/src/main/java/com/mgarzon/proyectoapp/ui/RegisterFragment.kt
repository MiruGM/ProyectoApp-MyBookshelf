package com.mgarzon.proyectoapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mgarzon.proyectoapp.R
import com.mgarzon.proyectoapp.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment(R.layout.fragment_register) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentRegisterBinding.bind(view).apply {

            btnRegister.setOnClickListener {
                val user = etUser.text.toString()
                val mail = etMail.text.toString()
                val password = etPassword.text.toString()
                val password2 = etPassword2.text.toString()

                if(user.isNotEmpty() && mail.isNotEmpty() && password.isNotEmpty() && password2.isNotEmpty()){
                    if(password == password2){
                        findNavController().navigate(
                            R.id.action_registerFragment_to_mainPageFragment
                        )
                    }else{
                        etPassword2.error = "Las contraseñas no coinciden"
                    }
                } else {
                    if (user.isEmpty()) {
                        etUser.error = "Ingrese un usuario"
                    } else if (mail.isEmpty()) {
                        etMail.error = "Ingrese un correo"
                    } else if (password.isEmpty()) {
                        etPassword.error = "Ingrese una contraseña"
                    } else if (password2.isEmpty()) {
                        etPassword2.error = "Ingrese una contraseña"
                    }

                }
            }

            btnCancel.setOnClickListener {
                findNavController().navigate(
                    R.id.action_registerFragment_to_loginFragment
                )
            }
        }
    }
}