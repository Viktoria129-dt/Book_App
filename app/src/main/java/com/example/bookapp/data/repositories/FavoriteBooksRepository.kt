package com.example.bookapp.data.repositories

import com.example.bookapp.data.local.dao.FavoriteBookDao
import com.example.bookapp.data.local.entity.FavoriteBook
import kotlinx.coroutines.flow.Flow

class FavoriteBooksRepository(
    private val dao: FavoriteBookDao
) {

    suspend fun addToFavorites(book: FavoriteBook) {
        dao.insert(book)
    }

    suspend fun removeFromFavorites(bookId: String) {
        dao.deleteById(bookId)
    }

    fun getFavorites(): Flow<List<FavoriteBook>> = dao.getFavoriteBooks()
}