package com.biblioteca.app.data.repository

import android.content.Context
import android.content.SharedPreferences

class PreferenciasRepository(contexto: Context) {
    private val prefs: SharedPreferences = 
        contexto.getSharedPreferences("biblioteca_prefs", Context.MODE_PRIVATE)
    
    fun guardarToken(token: String) {
        prefs.edit().putString("auth_token", token).apply()
    }
    
    fun obtenerToken(): String? {
        return prefs.getString("auth_token", null)
    }
    
    fun guardarImagenPerfil(uri: String) {
        prefs.edit().putString("imagen_perfil", uri).apply()
    }
    
    fun obtenerImagenPerfil(): String? {
        return prefs.getString("imagen_perfil", null)
    }
    
    fun limpiar() {
        prefs.edit().clear().apply()
    }
}
