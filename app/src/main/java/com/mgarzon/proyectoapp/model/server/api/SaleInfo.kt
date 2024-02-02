package com.mgarzon.proyectoapp.model.server.api

data class SaleInfo(
    val buyLink: String,
    val country: String,
    val isEbook: Boolean,
    val listPrice: ListPrice,
    val retailPrice: RetailPrice,
    val saleability: String
)