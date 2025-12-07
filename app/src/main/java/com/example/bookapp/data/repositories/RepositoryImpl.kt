package com.example.bookapp.data.repositories

import com.example.bookapp.data.dto.SearchedBookDto
import com.example.bookapp.data.network.KtorRemoteBookDataSource

class RepositoryImpl(
    private val dataSource: KtorRemoteBookDataSource
): Repository {
    override suspend fun searchBooks(query: String): List<SearchedBookDto> {
        return dataSource.searchBooks(query)
    }
}