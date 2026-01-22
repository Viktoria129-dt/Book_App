package com.example.bookapp.data.mapper

import com.example.bookapp.data.local.entity.FavoriteBook
import com.example.bookapp.domain.Book

fun FavoriteBook.toDomain(): Book {
    return Book(
        id = this.id,
        title = this.title,
        imageUrl = this.coverUrl,
        authors = this.author?.let { listOf(it) } ?: emptyList(),
        description = null,          // нет данных в FavoriteBook
        languages = null,            // нет данных в FavoriteBook
        firstPublishYear = null,     // нет данных
        averageRating = null,        // нет данных
        ratingCount = null,          // нет данных
        numPages = null,             // нет данных
        numEditions = null
    )
}