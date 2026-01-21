package com.example.bookapp.presentation.BookDetails

import com.example.bookapp.domain.Book

data class BookDetailsState (
    val book: Book? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
    val isFavorite: Boolean = false
)