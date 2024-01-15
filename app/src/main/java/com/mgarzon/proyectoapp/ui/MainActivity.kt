package com.mgarzon.proyectoapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mgarzon.proyectoapp.R
import com.mgarzon.proyectoapp.databinding.ActivityMainBinding
import com.mgarzon.proyectoapp.ui.detail.DetailFragment
import com.mgarzon.proyectoapp.ui.main.MainPageFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)

            //Eliminar la sombra en el diseño del menú
            bottomNavigationView?.background = null

            //Eliminar el click del elemento vacío del menú inferior
            bottomNavigationView?.menu?.getItem(3)?.isEnabled = false

            //Mostrar el fragmento MainPageFragment por primera vez y ocultar menú
            //replaceFragment(MainPageFragment())
            //coordinatorLayout?.visibility = View.GONE

            //Navegar entre los fragmentos del menú inferior
            bottomNavigationView?.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home -> {
                        replaceFragment(MainPageFragment())
                        true
                    }
                    R.id.notif -> {
                        replaceFragment(NotificationsFragment())
                        true
                    }
                    R.id.profile -> {
                        replaceFragment(UserFragment())
                        true
                    }
                    else -> false
                }
            }

            //Sacar el menú al pulsar el boton +
            addItem?.setOnClickListener {
                showBottomDialog()
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun showBottomDialog() {
        val view: View = layoutInflater.inflate(R.layout.elem_bottom_sheet_dialog, null)
        val dialog = BottomSheetDialog(this, R.style.BottomSheetDialog)
        dialog.setContentView(view)
        dialog.show()

    }
}