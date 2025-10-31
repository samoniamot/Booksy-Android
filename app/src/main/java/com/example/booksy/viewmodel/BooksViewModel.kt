package com.example.booksy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksy.data.local.AppDatabase
import com.example.booksy.data.local.BookEntity
import com.example.booksy.data.remote.ApiService
import com.example.booksy.data.remote.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val apiService: ApiService,
    private val database: AppDatabase
) : ViewModel() {

    private val _booksState = MutableStateFlow<BooksState>(BooksState.Loading)
    val booksState: StateFlow<BooksState> = _booksState

    private val _filter = MutableStateFlow("all")
    val filter: StateFlow<String> = _filter

    private val _cartCount = MutableStateFlow(0)
    val cartCount: StateFlow<Int> = _cartCount

    init {
        loadBooks()
    }

    fun loadBooks() {
        viewModelScope.launch {
            _booksState.value = BooksState.Loading
            try {
                // cargar libros de API
                val response = apiService.getBooks()
                if (response.isSuccessful) {
                    response.body()?.let { apiBooks ->
                        // guardar en DB local
                        val entities = apiBooks.map { it.toEntity() }
                        database.bookDao().insertBooks(entities)
                    }
                }

                // combinar libros de API y locales
                val allBooks = database.bookDao().getAllBooks()
                val userBooks = database.bookDao().getUserBooks()

                combine(allBooks, userBooks, _filter) { api, user, filt ->
                    val combined = api + user
                    if (filt == "all") combined else combined.filter { it.categoria == filt }
                }.collect { filteredBooks ->
                    _booksState.value = BooksState.Success(filteredBooks)
                }

            } catch (e: Exception) {
                // fallback a libros locales
                val localBooks = database.bookDao().getAllBooks()
                combine(localBooks, _filter) { books, filt ->
                    if (filt == "all") books else books.filter { it.categoria == filt }
                }.collect { filteredBooks ->
                    _booksState.value = BooksState.Success(filteredBooks)
                }
            }
        }
    }

    fun setFilter(newFilter: String) {
        _filter.value = newFilter
    }

    fun addToCart() {
        _cartCount.value += 1
    }

    fun markAsRead(bookId: String) {
        viewModelScope.launch {
            val book = database.bookDao().getAllBooks().value?.find { it.id == bookId }
            book?.let {
                val updated = it.copy(isRead = !it.isRead)
                database.bookDao().updateBook(updated)
            }
        }
    }

    fun addUserBook(titulo: String, autor: String, categoria: String, precio: Double, imagen: String?) {
        viewModelScope.launch {
            val newBook = BookEntity(
                id = System.currentTimeMillis().toString(),
                titulo = titulo,
                autor = autor,
                categoria = categoria,
                precio = precio,
                urlImagen = imagen,
                isUserBook = true
            )
            database.bookDao().insertBook(newBook)
        }
    }

    fun deleteUserBook(bookId: String) {
        viewModelScope.launch {
            database.bookDao().deleteBook(bookId)
        }
    }

    private fun Book.toEntity() = BookEntity(
        id = id,
        titulo = titulo,
        autor = autor,
        categoria = categoria,
        precio = precio,
        urlImagen = urlImagen
    )
}

sealed class BooksState {
    object Loading : BooksState()
    data class Success(val books: List<BookEntity>) : BooksState()
    data class Error(val message: String) : BooksState()
}