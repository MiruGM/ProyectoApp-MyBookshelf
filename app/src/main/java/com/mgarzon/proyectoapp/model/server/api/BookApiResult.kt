package com.mgarzon.proyectoapp.model.server.api

data class BookApiResult(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)