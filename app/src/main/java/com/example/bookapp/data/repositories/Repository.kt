package com.example.bookapp.data.repositories

import com.example.bookapp.domain.Book

interface Repository {
    suspend fun searchBooks(query: String): List<Book>
    suspend fun getBookDetails(bookId: String): Book
}