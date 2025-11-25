package com.example.bookapp.presentation.BookList.components
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookapp.domain.Book
import com.example.bookapp.presentation.BookList.BookListScreen
import com.example.bookapp.presentation.BookList.BookListState
import com.example.bookapp.ui.theme.BookAppTheme


@Preview
@Composable
private fun BookSearchBarPreview(){
    BookAppTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ){
            BookSearchBar(
                searchQuery = "",
                onIamSearch = {},
                OnSearchQueryChange = {},
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

private val books = (1..100).map{
    Book(
        id = it.toString(),
        title = "Book $it",
        imageUrl= "https://test.com",
        authors = listOf("Philipp Lackner"), 
        description = "Description $it",
        languages = emptyList(),
        firstPublishYear = null,
        averageRating = 4.67854,
        ratingCount = 5,
        numPages = 100,
        numEditions = 3
    )
}

@Preview
@Composable
private fun BookListScreenPreview(){
    BookListScreen(
        state = BookListState(
            searchResults = books
        ),
        onAction = {}
    )
}