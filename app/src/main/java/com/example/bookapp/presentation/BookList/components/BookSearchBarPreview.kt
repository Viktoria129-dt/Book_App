package com.example.bookapp.presentation.BookList.components
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookapp.ui.theme.BookAppTheme
import com.example.bookapp.ui.theme.DarkBlue


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