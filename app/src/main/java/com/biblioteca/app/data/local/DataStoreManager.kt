package com.biblioteca.app.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "configuracion")

class DataStoreManager(private val context: Context) {
    
    companion object {
        private val TOKEN_KEY = stringPreferencesKey("token_sesion")
        private val IMAGEN_KEY = stringPreferencesKey("imagen_perfil")
        private val RECORDAR_KEY = booleanPreferencesKey("recordar_sesion")
    }
    
    // guardar token
    suspend fun guardarToken(token: String) {
        context.dataStore.edit { preferencias ->
            preferencias[TOKEN_KEY] = token
        }
    }
    
    // obtener token
    val token: Flow<String?> = context.dataStore.data.map { preferencias ->
        preferencias[TOKEN_KEY]
    }
    
    // guardar imagen
    suspend fun guardarImagen(uri: String) {
        context.dataStore.edit { preferencias ->
            preferencias[IMAGEN_KEY] = uri
        }
    }
    
    // obtener imagen
    val imagenPerfil: Flow<String?> = context.dataStore.data.map { preferencias ->
        preferencias[IMAGEN_KEY]
    }
    
    // recordar sesion
    suspend fun guardarRecordarSesion(recordar: Boolean) {
        context.dataStore.edit { preferencias ->
            preferencias[RECORDAR_KEY] = recordar
        }
    }
    
    val recordarSesion: Flow<Boolean> = context.dataStore.data.map { preferencias ->
        preferencias[RECORDAR_KEY] ?: false
    }
    
    // limpiar todo
    suspend fun limpiarTodo() {
        context.dataStore.edit { preferencias ->
            preferencias.clear()
        }
    }
}
