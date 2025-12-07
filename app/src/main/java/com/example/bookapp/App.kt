package com.example.bookapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookapp.data.network.KtorRemoteBookDataSource
import com.example.bookapp.data.repositories.RepositoryImpl
import com.example.bookapp.presentation.BookList.BookListScreenRoot
import com.example.bookapp.presentation.BookList.BookListViewModel

@Composable
@Preview
fun App(){

    val dataSource = remember { KtorRemoteBookDataSource() }

    val repository = remember { RepositoryImpl(dataSource) }

    val viewModel = remember { BookListViewModel(repository) }
    BookListScreenRoot(
        viewModel = viewModel,
        onBookClick = {
        }
    )
}