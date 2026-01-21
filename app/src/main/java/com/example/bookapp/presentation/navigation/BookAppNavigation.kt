package com.example.bookapp.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bookapp.presentation.BookDetails.BookDetailsScreen
import com.example.bookapp.presentation.BookList.BookListScreenRoot


@Composable
fun BookAppNavigation() {
    Log.d("NAV", "BookAppNavigation composed")

    val navigator = rememberNavController()

    NavHost(
        navController = navigator,
        startDestination = Screen.BookList.route
    ) {
        composable(Screen.BookList.route) {
            Log.d("NAV", "BookListScreen shown")

            BookListScreenRoot(
                onBookClick = { book ->
                    Log.d("NAV", "Book clicked: ${book.id}")
                    navigator.navigate(Screen.BookDetails.createRoute(book.id))
                }
            )
        }

        composable(
            route = "${Screen.BookDetails.route}/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.StringType })
        ) { backStackEntry ->
            val bookId = backStackEntry
                .arguments
                ?.getString("bookId")
                ?.replace("_", "/")

            if (bookId != null) {
                BookDetailsScreen(bookId = bookId)
            }
        }
    }
}

