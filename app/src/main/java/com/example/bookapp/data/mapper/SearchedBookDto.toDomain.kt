package com.example.bookapp.data.mapper

import com.example.bookapp.data.dto.SearchedBookDto
import com.example.bookapp.domain.Book

fun SearchedBookDto.toDomain(): Book {
    val imageUrl = coverKey?.let { "https://covers.openlibrary.org/b/olid/$it-L.jpg" }
    val authors = authorsNames?: emptyList()
    return Book(
        id = id,
        title = title,
        imageUrl = imageUrl,
        authors = authors,
        description = null,
        languages = language,
        firstPublishYear = firstPublishYear,
        averageRating = ratingsAverage,
        ratingCount = ratingCount,
        numPages = numPagesMedian,
        numEditions = numEdition
    )
}