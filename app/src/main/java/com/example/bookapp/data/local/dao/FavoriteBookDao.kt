package com.example.bookapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.bookapp.data.local.entity.FavoriteBook
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteBookDao{
    @Query("SELECT * FROM favorite_books")
    fun getFavoriteBooks(): Flow<List<FavoriteBook>>

    @Insert(onConflict = REPLACE)
    suspend fun insert(book: FavoriteBook)

    @Query("DELETE FROM favorite_books WHERE id = :bookId")
    suspend fun deleteById(bookId: String)
}