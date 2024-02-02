package com.mgarzon.proyectoapp.model.server

import com.mgarzon.proyectoapp.model.server.api.BookApiResult
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteService {
    @GET("volumes?q=subject:romance&langRestrict=es")
    suspend fun listOfRecBooks(@Query("key")apiKey: String): BookApiResult
}