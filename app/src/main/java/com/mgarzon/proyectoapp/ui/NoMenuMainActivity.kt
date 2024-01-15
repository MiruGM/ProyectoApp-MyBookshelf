package com.mgarzon.proyectoapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mgarzon.proyectoapp.databinding.ActivityNoMenuMainBinding

class NoMenuMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityNoMenuMainBinding.inflate(layoutInflater).apply {
            setContentView(root)

        }
    }
}