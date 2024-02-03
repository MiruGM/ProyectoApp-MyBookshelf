package com.mgarzon.proyectoapp.model

data class User (
    var id: String? = null,
    val username: String,
    val name: String,
    val email: String,
    val password: String,
    val image: String? = "@drawable/user_default",
    val friendList: List<String>? = null,
    val bookmarkList: List<String>? = null
)