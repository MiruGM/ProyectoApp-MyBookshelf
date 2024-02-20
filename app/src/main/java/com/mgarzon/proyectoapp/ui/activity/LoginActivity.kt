package com.mgarzon.proyectoapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.GoogleAuthProvider
import com.mgarzon.proyectoapp.databinding.ActivityLoginBinding
import com.mgarzon.proyectoapp.firebase.AuthManager
import com.mgarzon.proyectoapp.firebase.AuthRes
import com.mgarzon.proyectoapp.firebase.FirestoreManager
import com.mgarzon.proyectoapp.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private val auth = AuthManager(this)
    private val db = FirestoreManager(this)
    private lateinit var  binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        if (auth.getCurrentUser() != null){
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }

        val googleSignLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            when (val account =
                auth.handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(result.data))) {
                is AuthRes.Success -> {
                    val credential = GoogleAuthProvider.getCredential(account.data?.idToken, null)
                    GlobalScope.launch {
                        val firebaseUser = auth.googleSignInCredential(credential)
                        when (firebaseUser) {
                            is AuthRes.Success -> {
                                Snackbar.make(
                                    binding.root,
                                    "Inicio de sesión correcto",
                                    Snackbar.LENGTH_SHORT
                                ).show()

                                //Cojo los valores de la cuenta de Google que se han guardado
                                // en Firebase al autenticarse con Google
                                val newUserFromGoogle = auth.getCurrentUser()

                                val uid = newUserFromGoogle?.uid
                                val displayName = newUserFromGoogle?.displayName
                                val email = newUserFromGoogle?.email
                                val photoUrl = newUserFromGoogle?.photoUrl.toString()

                                //Creo el usuario a guardar en la bd
                                val newUserToDatabase = User (
                                    id = uid,
                                    username = "Username",
                                    name = displayName.toString(),
                                    email = email.toString(),
                                    password = "google",
                                    image = photoUrl
                                )

                                //Guardo el usuario en la bd
                                if (uid != null) {
                                    db.saveUser(newUserToDatabase)
                                } else {
                                    Log.d("LoginActivity", "Error al obtener el UID del usuario desde Google")
                                }

                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                            }

                            is AuthRes.Error -> {
                                Snackbar.make(
                                    binding.root,
                                    "Error al iniciar sesión",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
                is AuthRes.Error -> {
                    Snackbar.make(binding.root, "Error al iniciar sesión", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        }


        with(binding) {
            btnLogin.setOnClickListener {
                val mail = etMail.text.toString()
                val password = etPassword.text.toString()
                
                mailPassLogin(mail, password)
            }
            
            btnLoginGoogle.setOnClickListener {
                auth.signInWithGoogle(googleSignLauncher)
            }
            
            btnRegister.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }

            twResetPassword.setOnClickListener {
                val intent = Intent(this@LoginActivity, ResetResetPasswordActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun mailPassLogin(mail: String, password: String) {
        if (mail.isNotEmpty() && password.isNotEmpty()) {

            GlobalScope.launch(Dispatchers.IO) {
                when (auth.signInWithEmailAndPassword(
                    mail,
                    password
                )) {
                    is AuthRes.Success -> {
                        Snackbar.make(
                            binding.root,
                            "Inicio de sesión correcto",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                    }

                    is AuthRes.Error -> {
                        Snackbar.make(
                            binding.root,
                            "Error al iniciar sesión",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}