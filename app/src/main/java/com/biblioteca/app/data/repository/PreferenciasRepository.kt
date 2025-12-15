package com.biblioteca.app.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.biblioteca.app.data.model.Rol

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
    
    fun guardarRol(rol: Rol) {
        prefs.edit().putString("rol_usuario", rol.name).apply()
    }
    
    fun obtenerRol(): Rol {
        val rolStr = prefs.getString("rol_usuario", Rol.LECTOR.name)
        return try {
            Rol.valueOf(rolStr ?: Rol.LECTOR.name)
        } catch (e: Exception) {
            Rol.LECTOR
        }
    }
    
    fun guardarNombreUsuario(nombre: String) {
        prefs.edit().putString("nombre_usuario", nombre).apply()
    }
    
    fun obtenerNombreUsuario(): String {
        return prefs.getString("nombre_usuario", "") ?: ""
    }
    
    fun guardarEmailUsuario(email: String) {
        prefs.edit().putString("email_usuario", email).apply()
    }
    
    fun obtenerEmailUsuario(): String {
        return prefs.getString("email_usuario", "") ?: ""
    }
    
    fun limpiar() {
        prefs.edit().clear().apply()
    }
}
