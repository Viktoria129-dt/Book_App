package com.example.bookapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_books")
data class FavoriteBook(
    @PrimaryKey val id: String,
    val title: String,
    val author: String?,
    val coverUrl: String?
)