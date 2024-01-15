package com.mgarzon.proyectoapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.mgarzon.proyectoapp.R
import com.mgarzon.proyectoapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var mActivity: Activity? = null

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
        menu?.visibility = View.GONE

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentLoginBinding.bind(view).apply {

            btnLogin.setOnClickListener {
                val user = etUser.text.toString()
                val password = etPassword.text.toString()
                if (user.isNotEmpty() && password.isNotEmpty()) {
                    /*val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)*/

                    //Cambiar la visibilidad del menú
                    //requireActivity().findViewById<CoordinatorLayout>(R.id.coordinatorLayout).visibility = View.VISIBLE

                   /* val menu: CoordinatorLayout = requireActivity().findViewById(R.id.coordinatorLayout)
                    menu.visibility = View.VISIBLE*/
                    //RequireActivity().findViewById(R.id.coordinatorLayout).visibility = View.VISIBLE
                    //getActivity.findViewById(R.id.your_view)


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