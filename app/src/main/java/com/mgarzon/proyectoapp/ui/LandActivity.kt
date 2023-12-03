package com.mgarzon.proyectoapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.mgarzon.proyectoapp.R
import com.mgarzon.proyectoapp.databinding.ActivityLandBinding

class LandActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityLandBinding.inflate(layoutInflater).apply {
            setContentView(root)

            delayStartIntent()
        }
    }

    fun delayStartIntent() {
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this@LandActivity, MainActivity::class.java)
            startActivity(intent)
        }, 3000)

    }

}