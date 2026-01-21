package com.example.bookapp.data.repositories

import com.example.bookapp.data.mapper.toDomain
import com.example.bookapp.data.network.KtorRemoteBookDataSource
import com.example.bookapp.domain.Book

class RepositoryImpl(
    private val dataSource: KtorRemoteBookDataSource
): Repository {
    override suspend fun searchBooks(query: String): List<Book> {
        val dtos = dataSource.searchBooks(query)
        return dtos.map { it.toDomain() }
    }

    override suspend fun getBookDetails(bookId: String): Book {
        val dto = dataSource.getBookDetails(bookId)
        return dto?.toDomain() ?: throw Exception("Book with id=$bookId wasn't found")
    }
}