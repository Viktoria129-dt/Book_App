package com.example.bookapp.presentation.BookDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookapp.data.local.entity.FavoriteBook
import com.example.bookapp.data.repositories.FavoriteBooksRepository
import com.example.bookapp.data.repositories.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BookDetailsViewModel(private val bookId: String, private val repository: Repository, private val favoriteRepository: FavoriteBooksRepository): ViewModel(
) {
    private val _state = MutableStateFlow(BookDetailsState())
    val state: StateFlow<BookDetailsState> = _state.asStateFlow()
    init {
        loadBookDetails()
        observeFavoriteStatus()
    }

    private fun observeFavoriteStatus() {
        viewModelScope.launch {
            favoriteRepository.getFavorites().collect { favorites ->
                val isFav = favorites.any { it.id == bookId }
                _state.value = _state.value.copy(isFavorite = isFav)
            }
        }
    }


    fun loadBookDetails(){
        _state.value = BookDetailsState(isLoading = true)
        viewModelScope.launch {
            try {
                val book = repository.getBookDetails(bookId)

                if (book != null) {
                    // проверяем, есть ли книга в избранном
                    val isFav = favoriteRepository.getFavorites()
                        .first() // берём первый эмит (Flow<List>) сразу
                        .any { it.id == book.id }

                    _state.value = BookDetailsState(
                        book = book,
                        isLoading = false,
                        isFavorite = isFav
                    )
                } else {
                    _state.value = BookDetailsState(
                        book = null,
                        isLoading = false,
                        error = "The book wasn't found"
                    )
                }
            }
            catch (e: Exception){
                _state.value = BookDetailsState(
                    book = null,
                    isLoading = false,
                    error = e.message?: "Download error"
                )
            }
        }
    }
    fun toggleFavorite() {
        viewModelScope.launch {
            state.value.book?.let { book ->
                if (state.value.isFavorite) {
                    favoriteRepository.removeFromFavorites(book.id)
                } else {
                    favoriteRepository.addToFavorites(
                        FavoriteBook(
                            id = book.id,
                            title = book.title,
                            author = book.authors.joinToString(", "),
                            coverUrl = book.imageUrl
                        )
                    )
                }
            }
        }
    }
}