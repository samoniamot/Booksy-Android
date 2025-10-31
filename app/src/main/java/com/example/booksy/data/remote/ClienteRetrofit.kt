package com.example.booksy.data.remote

import com.example.booksy.data.local.PreferenciasUsuario
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClienteRetrofit {
    private const val URL_BASE = "https://x8ki-letl-twmt.n7.xano.io/api:Rfm_61dW/"

    fun crear(preferenciasUsuario: PreferenciasUsuario): ServicioApi {
        val cliente = OkHttpClient.Builder()
            .addInterceptor(InterceptorAuth(preferenciasUsuario))
            .build()

        return Retrofit.Builder()
            .baseUrl(URL_BASE)
            .client(cliente)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ServicioApi::class.java)
    }
}