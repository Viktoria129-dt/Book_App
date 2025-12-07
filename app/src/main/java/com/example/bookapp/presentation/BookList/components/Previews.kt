package com.example.bookapp.presentation.BookList.components
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookapp.domain.Book
import com.example.bookapp.presentation.BookList.BookListScreen
import com.example.bookapp.presentation.BookList.BookListState
import com.example.bookapp.ui.theme.BookAppTheme


@Preview(showBackground = true, name = "BookSearchBar")
@Composable
private fun BookSearchBarPreview() {
    BookAppTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Color.White)
        ) {
            BookSearchBar(
                searchQuery = "Search books...",
                onIamSearch = {},
                OnSearchQueryChange = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


private val previewBooks = (1..5).map {
    Book(
        id = it.toString(),
        title = "Book Title $it",
        imageUrl = "https://covers.openlibrary.org/b/id/8259447-L.jpg",
        authors = listOf("Author $it"),
        description = "This is a sample book description for preview purposes.",
        languages = listOf("English"),
        firstPublishYear = 2020,
        averageRating = 4.2,
        ratingCount = 150,
        numPages = 300,
        numEditions = 2
    )
}

@Preview(showBackground = true, name = "BookListScreen - Results", device = "spec:width=411dp,height=891dp")
@Composable
private fun BookListScreenPreview() {
    BookAppTheme {
        BookListScreen(
            state = BookListState(
                searchQuery = "Android",
                searchResults = previewBooks.take(10),
                favoriteBooks = previewBooks.take(2),
            ),
            onAction = {}
        )
    }
}

@Preview(showBackground = true, name = "BookListScreen - Loading", device = "spec:width=411dp,height=891dp")
@Composable
private fun BookListScreenLoadingPreview() {
    BookAppTheme {
        BookListScreen(
            state = BookListState(
                searchQuery = "Kotlin",
                isLoading = true,
            ),
            onAction = {}
        )
    }
}

@Preview(showBackground = true, name = "BookListScreen - Favorites", device = "spec:width=411dp,height=891dp")
@Composable
private fun BookListScreenFavoritesPreview() {
    BookAppTheme {
        BookListScreen(
            state = BookListState(
                favoriteBooks = previewBooks,
                selectedTabIndex = 1,
            ),
            onAction = {}
        )
    }
}

@Preview(showBackground = true, name = "BookListScreen - Empty", device = "spec:width=411dp,height=891dp")
@Composable
private fun BookListScreenEmptyPreview() {
    BookAppTheme {
        BookListScreen(
            state = BookListState(
                searchQuery = "Unknown Book",
            ),
            onAction = {}
        )
    }
}
