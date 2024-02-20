package com.mgarzon.proyectoapp.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mgarzon.proyectoapp.R
import com.mgarzon.proyectoapp.databinding.ActivityMainBinding
import com.mgarzon.proyectoapp.ui.fragment.AddFriendFragment
import com.mgarzon.proyectoapp.ui.fragment.NotificationsFragment
import com.mgarzon.proyectoapp.ui.fragment.UserFragment
import com.mgarzon.proyectoapp.ui.fragment.addedit.AddEditReviewFragment
import com.mgarzon.proyectoapp.ui.fragment.main.MainPageFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)

            //Eliminar la sombra en el diseño del menú
            bottomNavigationView?.background = null

            //Eliminar el click del elemento vacío del menú inferior
            bottomNavigationView?.menu?.getItem(3)?.isEnabled = false

            //Navegar entre los fragmentos del menú inferior
            bottomNavigationView?.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home -> {
                        //Cambiar el fondo
                        val layout = findViewById<View>(R.id.constrainLayout) as ConstraintLayout
                        layout.setBackgroundResource(R.drawable.img_fondo_1)
                        //Cambiar el fragmento
                        replaceFragment(MainPageFragment())
                        true
                    }
                    R.id.notif -> {
                        //Cambiar el fondo
                        val layout = findViewById<View>(R.id.constrainLayout) as ConstraintLayout
                        layout.setBackgroundResource(R.drawable.img_fondo_1)
                        //Cambiar el frarmento
                        replaceFragment(NotificationsFragment())
                        true
                    }
                    R.id.profile -> {
                        //Cambiar el fondo
                        val layout = findViewById<View>(R.id.constrainLayout) as ConstraintLayout
                        layout.setBackgroundResource(R.drawable.img_fondo_2)
                        //Cambiar el fragmento
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
        val view: View = layoutInflater.inflate(R.layout.view_bottom_sheet_dialog, null)
        val dialog = BottomSheetDialog(this, R.style.BottomSheetDialog)
        dialog.setContentView(view)
        dialog.show()

        //Botón añadir reseña
        val btnAddReview = view.findViewById<LinearLayout>(R.id.linearAddReview)
        btnAddReview.setOnClickListener {
            replaceFragment(AddEditReviewFragment())
            dialog.dismiss()
        }

        //Botón añadir amigo
        val btnAddFriend = view.findViewById<LinearLayout>(R.id.linearAddFriend)
        btnAddFriend.setOnClickListener {
            replaceFragment(AddFriendFragment())
            dialog.dismiss()
        }
    }
}