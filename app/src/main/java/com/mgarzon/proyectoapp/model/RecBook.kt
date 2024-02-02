package com.mgarzon.proyectoapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecBook(
    val title: String,
    val author: List<String>,
    val genre: List<String>,
    /*val publisher: String,*/
    val publishingDate: String,
    val pageCount: Int,
    /*val synopsis: String,*/
    val urlCover: String
): Parcelable