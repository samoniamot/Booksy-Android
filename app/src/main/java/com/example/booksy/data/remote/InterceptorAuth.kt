package com.example.booksy.data.remote

import com.example.booksy.data.local.PreferenciasUsuario
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class InterceptorAuth(private val preferenciasUsuario: PreferenciasUsuario) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { preferenciasUsuario.tokenAuth.first() }
        val solicitud = if (token != null) {
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            chain.request()
        }
        return chain.proceed(solicitud)
    }
}