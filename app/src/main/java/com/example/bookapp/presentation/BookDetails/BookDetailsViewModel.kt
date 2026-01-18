package com.example.bookapp.presentation.BookDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookapp.data.repositories.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookDetailsViewModel(private val bookId: String, private val repository: Repository): ViewModel(
) {
    private val _state = MutableStateFlow(BookDetailsState())
    val state: StateFlow<BookDetailsState> = _state.asStateFlow()
    init {
        loadBookDetails()
    }

    fun loadBookDetails(){
        _state.value = BookDetailsState(isLoading = true)
        viewModelScope.launch {
            try {
                val book = repository.getBookDetails(bookId)

                if (book != null) {
                    _state.value = BookDetailsState(
                        book = book,
                        isLoading = false,
                        isFavorite = false
                    )
                }
                else{
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
    fun toggleFavorite(){
        _state.value = _state.value.copy(
            isFavorite = !_state.value.isFavorite
        )
    }
}