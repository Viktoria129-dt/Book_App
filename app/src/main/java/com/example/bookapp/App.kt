package com.example.bookapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookapp.presentation.BookList.BookListScreenRoot
import com.example.bookapp.presentation.BookList.BookListViewModel

@Composable
@Preview
fun App(){
    BookListScreenRoot(
        viewModel = remember { BookListViewModel() },
        onBookClick = {
        }
    )
}