package com.biblioteca.app.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LibrosRetrofitClient {
    
    private const val URL_BASE = "https://jsonplaceholder.typicode.com/"
    
    val servicioLibros: LibrosApiService by lazy {
        Retrofit.Builder()
            .baseUrl(URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LibrosApiService::class.java)
    }
}
