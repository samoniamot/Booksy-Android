package com.example.booksy.di

import android.content.Context
import androidx.room.Room
import com.example.booksy.data.local.AppDatabase
import com.example.booksy.data.local.PreferenciasUsuario
import com.example.booksy.data.remote.ClienteRetrofit
import com.example.booksy.data.remote.ServicioApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuloApp {

    @Provides
    @Singleton
    fun proporcionarPreferenciasUsuario(@ApplicationContext contexto: Context): PreferenciasUsuario {
        return PreferenciasUsuario(contexto)
    }

    @Provides
    @Singleton
    fun proporcionarServicioApi(preferenciasUsuario: PreferenciasUsuario): ServicioApi {
        return ClienteRetrofit.crear(preferenciasUsuario)
    }

    @Provides
    @Singleton
    fun proporcionarBaseDatos(@ApplicationContext contexto: Context): AppDatabase {
        return Room.databaseBuilder(
            contexto,
            AppDatabase::class.java,
            "base_datos_booksy"
        ).build()
    }
}