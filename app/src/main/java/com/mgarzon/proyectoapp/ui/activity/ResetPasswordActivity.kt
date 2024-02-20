package com.mgarzon.proyectoapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.mgarzon.proyectoapp.databinding.ActivityResetPasswordBinding
import com.mgarzon.proyectoapp.firebase.AuthManager
import com.mgarzon.proyectoapp.firebase.AuthRes
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ResetResetPasswordActivity : AppCompatActivity() {

    private val auth = AuthManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityResetPasswordBinding.inflate(layoutInflater).apply {
            setContentView(root)

            btnReset.setOnClickListener {
                GlobalScope.launch {
                    when (auth.resetPassword(etMail.text.toString())){
                        is AuthRes.Success -> {
                            Snackbar.make(root, "Correo enviado correctamente", Snackbar.LENGTH_SHORT).show()
                            finish()
                        }
                        is AuthRes.Error -> {
                            Snackbar.make(root, "Error al enviar el correo", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}