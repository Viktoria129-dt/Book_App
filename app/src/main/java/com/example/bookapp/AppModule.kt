package com.example.bookapp

import com.example.bookapp.data.network.KtorRemoteBookDataSource
import com.example.bookapp.data.repositories.Repository
import com.example.bookapp.data.repositories.RepositoryImpl
import com.example.bookapp.presentation.BookDetails.BookDetailsViewModel
import com.example.bookapp.presentation.BookList.BookListViewModel
import io.ktor.client.HttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import androidx.room.Room
import com.example.bookapp.data.local.database.AppDataBase
import com.example.bookapp.data.repositories.FavoriteBooksRepository

val appModule = module {

    single {
        HttpClient()
    }
    single {
        KtorRemoteBookDataSource()
    }

    // Repository
    single<Repository> {
        RepositoryImpl(
            dataSource = get()
        )
    }

    // Book list
    viewModel {
        BookListViewModel(
            repository = get(),
            favoriteBooksRepository = get()
        )
    }

    // Book details (с параметром)
    viewModel { (bookId: String) ->
        BookDetailsViewModel(
            bookId = bookId,
            repository = get(),
            favoriteRepository = get()
        )
    }
    single {
        Room.databaseBuilder(
            get(),
            AppDataBase::class.java,
            "book_database"
        ).build()
    }

    single {
        get<AppDataBase>().favoriteBookDao()
    }


    single {
        FavoriteBooksRepository(
            dao = get()
        )
    }
}