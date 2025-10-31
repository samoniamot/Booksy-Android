package com.example.booksy.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.almacenDatos: DataStore<Preferences> by preferencesDataStore(name = "preferencias_usuario")

class PreferenciasUsuario(private val contexto: Context) {

    companion object {
        private val TOKEN_AUTH = stringPreferencesKey("token_auth")
        private val ID_USUARIO = stringPreferencesKey("id_usuario")
        private val EMAIL_USUARIO = stringPreferencesKey("email_usuario")
        private val NOMBRE_USUARIO = stringPreferencesKey("nombre_usuario")
        private val AVATAR_USUARIO = stringPreferencesKey("avatar_usuario")
    }

    val tokenAuth: Flow<String?> = contexto.almacenDatos.data.map { it[TOKEN_AUTH] }
    val idUsuario: Flow<String?> = contexto.almacenDatos.data.map { it[ID_USUARIO] }
    val emailUsuario: Flow<String?> = contexto.almacenDatos.data.map { it[EMAIL_USUARIO] }
    val nombreUsuario: Flow<String?> = contexto.almacenDatos.data.map { it[NOMBRE_USUARIO] }
    val avatarUsuario: Flow<String?> = contexto.almacenDatos.data.map { it[AVATAR_USUARIO] }

    suspend fun guardarDatosAuth(token: String, idUsuario: String, email: String, nombre: String, avatar: String? = null) {
        contexto.almacenDatos.edit { prefs ->
            prefs[TOKEN_AUTH] = token
            prefs[ID_USUARIO] = idUsuario
            prefs[EMAIL_USUARIO] = email
            prefs[NOMBRE_USUARIO] = nombre
            if (avatar != null) prefs[AVATAR_USUARIO] = avatar
        }
    }

    suspend fun limpiarDatosAuth() {
        contexto.almacenDatos.edit { it.clear() }
    }
}