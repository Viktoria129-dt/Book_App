package com.example.bookapp.presentation.navigation

sealed class Screen(val route: String) {
    object BookList : Screen("book_list")
    object BookDetails : Screen("book_details") {

        fun createRoute(id: String): String {
            val safeId = id.replace("/", "_")
            return "$route/$safeId"
        }
    }

}