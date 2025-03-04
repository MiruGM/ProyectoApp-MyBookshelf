package com.mgarzon.proyectoapp

import android.app.Application
import com.mgarzon.proyectoapp.firebase.AuthManager
import com.mgarzon.proyectoapp.firebase.FirestoreManager

class App: Application() {

    lateinit var auth: AuthManager
    lateinit var firestore: FirestoreManager

    override fun onCreate() {
        super.onCreate()
        auth = AuthManager(this)
        firestore = FirestoreManager(this)
    }
}