package com.mgarzon.proyectoapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review (
    val title: String,
    val author: String,
    val genre: String,
    val review: String,
    val readAgain: Boolean,
    val urlCover: String,
    val rating: Float = 0f
): Parcelable