package com.example.bookapp.presentation.BookList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import com.example.bookapp.R
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bookapp.domain.Book
import com.example.bookapp.presentation.BookList.components.BookList
import com.example.bookapp.presentation.BookList.components.BookSearchBar
import com.example.bookapp.ui.theme.DarkBlue
import com.example.bookapp.ui.theme.DesertWhite
import com.example.bookapp.ui.theme.Sandyellow
import org.koin.androidx.compose.koinViewModel

@Composable
fun BookListScreenRoot(
    viewModel: BookListViewModel = koinViewModel(),
    onBookClick: (Book) -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()
    BookListScreen(
        state = state,
        onAction = { action ->
            when(action){
                is BookListAction.OnBookClick -> onBookClick(action.book)
                else -> viewModel.onAction(action)
            }
        }
    )
}



@Composable
fun BookListScreen(state: BookListState, onAction: (BookListAction) -> Unit) {
    val keyboardController = if (LocalInspectionMode.current) {
        null
    } else {
        LocalSoftwareKeyboardController.current
    }

    val pagerState = rememberPagerState(initialPage = state.selectedTabIndex) { 2 }
    val resultsBookScrollState = rememberLazyListState()
    val favouriteBookScrollState = rememberLazyListState()


    LaunchedEffect(state.selectedTabIndex) {
        if (pagerState.currentPage != state.selectedTabIndex) {
            pagerState.animateScrollToPage(state.selectedTabIndex)
        }
    }
    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage != state.selectedTabIndex) {
            onAction(BookListAction.OnTabSelected(pagerState.currentPage))
        }
    }

    LaunchedEffect(state.searchResults) {
        resultsBookScrollState.animateScrollToItem(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BookSearchBar(
            searchQuery = state.searchQuery,
            OnSearchQueryChange = {
                onAction(BookListAction.OnSearchQueryChange(it))
            },
            onIamSearch = {
                keyboardController?.hide()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .widthIn(max = 400.dp)
        )
        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            color = DesertWhite,
            shape = RoundedCornerShape(
                topStart = 32.dp,
                topEnd = 32.dp
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TabRow(
                    selectedTabIndex = pagerState.currentPage, // Используем currentPage из pagerState
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .fillMaxWidth(),
                    contentColor = DesertWhite,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            color = Sandyellow,
                            modifier = Modifier
                                .tabIndicatorOffset(currentTabPosition = tabPositions[pagerState.currentPage])
                        )
                    }
                ) {
                    Tab(
                        selected = pagerState.currentPage == 0,
                        onClick = {
                            onAction(BookListAction.OnTabSelected(0))
                        },
                        modifier = Modifier
                            .weight(1f),
                        selectedContentColor = Sandyellow,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f)
                    ) {
                        Text(
                            text = if (LocalInspectionMode.current) "Search Results" else stringResource(R.string.search_results),
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                        )
                    }

                    Tab(
                        selected = pagerState.currentPage == 1,
                        onClick = {
                            onAction(BookListAction.OnTabSelected(1))
                        },
                        modifier = Modifier
                            .weight(1f),
                        selectedContentColor = Sandyellow,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f)
                    ) {
                        Text(
                            text = if (LocalInspectionMode.current) "Favourites" else stringResource(R.string.favourites),
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) { pageIndex ->
                    when(pageIndex) {
                        0 -> {
                            if (state.isLoading) {
                                CircularProgressIndicator()
                            } else {
                                when {
                                    state.errorMessage != null -> {
                                        Text(
                                            text = if (LocalInspectionMode.current) "Error message" else state.errorMessage.asString(),
                                            textAlign = TextAlign.Center,
                                            color = MaterialTheme.colorScheme.error,
                                            style = MaterialTheme.typography.headlineSmall
                                        )
                                    }
                                    state.searchResults.isEmpty() -> {
                                        Text(
                                            text = if (LocalInspectionMode.current) "No results found" else stringResource(R.string.error_no_results),
                                            textAlign = TextAlign.Center,
                                            color = MaterialTheme.colorScheme.error,
                                            style = MaterialTheme.typography.headlineSmall
                                        )
                                    }
                                    else -> {
                                        BookList(
                                            books = state.searchResults,
                                            onBookClick = {
                                                onAction(BookListAction.OnBookClick(it))
                                            },
                                            scrollState = resultsBookScrollState,
                                            modifier = Modifier
                                                .fillMaxSize()
                                        )
                                    }
                                }
                            }
                        }
                        1 -> {
                            if (state.favoriteBooks.isEmpty()) {
                                Text(
                                    text = if (LocalInspectionMode.current) "No favorite books" else stringResource(R.string.no_favorite_books),
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.headlineSmall
                                )
                            } else {
                                BookList(
                                    books = state.favoriteBooks,
                                    onBookClick = {
                                        onAction(BookListAction.OnBookClick(it))
                                    },
                                    scrollState = favouriteBookScrollState,
                                    modifier = Modifier
                                        .fillMaxSize()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}