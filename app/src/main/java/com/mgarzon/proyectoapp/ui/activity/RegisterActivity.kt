package com.mgarzon.proyectoapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import com.mgarzon.proyectoapp.databinding.ActivityRegisterBinding
import com.mgarzon.proyectoapp.firebase.AuthManager
import com.mgarzon.proyectoapp.firebase.AuthRes
import com.mgarzon.proyectoapp.firebase.FirestoreManager
import com.mgarzon.proyectoapp.model.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private val auth = AuthManager(this)
    private val db = FirestoreManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnRegister.setOnClickListener {
                val user = etUser.text.toString()
                val name = etName.text.toString()
                val mail = etMail.text.toString()
                val password = etPassword.text.toString()
                val password2 = etPassword2.text.toString()

                register(user, name, mail, password, password2)
            }

            btnCancel.setOnClickListener {
                finish()
            }
        }
    }

    private fun ActivityRegisterBinding.register(
        user: String,
        name: String,
        mail: String,
        password: String,
        password2: String
    ) {
        if (user.isNotEmpty() && mail.isNotEmpty() && password.isNotEmpty() && password2.isNotEmpty()) {
            if (password == password2) {
                GlobalScope.launch {

                    when (auth.createUserWithEmailAndPassword(
                        mail,
                        password
                    )) {
                        is AuthRes.Success -> {
                            //Muestro que se ha creado correctamente
                            Snackbar.make(
                                root,
                                "Usuario creado correctamente",
                                Snackbar.LENGTH_LONG
                            )
                                .show()
                            //Recoger el UID del usuario de Firebase
                            val currentUserUID = auth.getCurrentUser()?.uid
                            //Crear el usuario a partir del UID y los campos:
                            val newUser = User(
                                id = currentUserUID,
                                username = user,
                                name = name,
                                email = mail,
                                password = password
                            )
                            //Guardar el usuario en Firestore si el UID no es null
                            if (currentUserUID != null) {
                                db.saveUser(newUser)
                            } else {
                                Log.d("RegisterActivity", "Error al obtener el UID del usuario")
                            }

                            finish()
                        }

                        is AuthRes.Error -> {
                            Snackbar.make(root, "Error al crear el usuario", Snackbar.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            } else {
                Snackbar.make(root, "Las contraseñas no coinciden", Snackbar.LENGTH_SHORT).show()
            }
        } else {
            Snackbar.make(root, "Debes rellenar todos los campos", Snackbar.LENGTH_SHORT).show()

        }
    }
}