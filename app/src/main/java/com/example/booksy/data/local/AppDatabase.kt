package com.example.booksy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LibroDao {
    @Insert
    suspend fun insertarLibros(libros: List<EntidadLibro>)

    @Update
    suspend fun actualizarLibro(libro: EntidadLibro)

    @Delete
    suspend fun eliminarLibro(libro: EntidadLibro)

    @Query("SELECT * FROM libros")
    fun obtenerTodosLibros(): Flow<List<EntidadLibro>>

    @Query("SELECT * FROM libros WHERE id = :id")
    suspend fun obtenerLibroPorId(id: String): EntidadLibro?
}

@Database(entities = [EntidadLibro::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun libroDao(): LibroDao
}
