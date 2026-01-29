package com.example.bookapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bookapp.data.local.dao.FavoriteBookDao
import com.example.bookapp.data.local.entity.FavoriteBook

@Database(
    entities = [FavoriteBook::class],
    version = 1,
    exportSchema = false
)

abstract class AppDataBase: RoomDatabase() {
    abstract fun favoriteBookDao(): FavoriteBookDao
}
