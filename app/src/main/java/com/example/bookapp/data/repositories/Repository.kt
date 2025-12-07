package com.example.bookapp.data.repositories

import com.example.bookapp.data.dto.SearchedBookDto

interface Repository {
    suspend fun searchBooks(query: String): List<SearchedBookDto>
}