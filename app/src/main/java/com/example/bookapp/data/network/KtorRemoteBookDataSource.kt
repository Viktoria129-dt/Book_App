package com.example.bookapp.data.network

import android.util.Log
import com.example.bookapp.data.dto.SearchedBookDto
import com.example.bookapp.data.dto.SearchedResponse
import com.example.bookapp.domain.Book
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlin.collections.emptyList


class KtorRemoteBookDataSource {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    suspend fun searchBooks(query: String): List<SearchedBookDto> {
        return try {
            if (query.isBlank()) {
                Log.d("SEARCH", "🌐 Запрос пустой")
                return emptyList()
            }

            val searchQuery = query.replace(" ", "+")
            val url = "https://openlibrary.org/search.json?q=$searchQuery&limit=10"
            Log.d("SEARCH", "🌐 URL: $url")

            Log.d("SEARCH", "🌐 Делаю Ktor запрос...")

            val response = client.get {
                url(url)
            }

            Log.d("SEARCH", "🌐 Статус ответа: ${response.status}")

            val searchedResponse: SearchedResponse = response.body()

            Log.d("SEARCH", "🌐 ✅ УСПЕХ! Найдено: ${searchedResponse.results.size}")

            if (searchedResponse.results.isNotEmpty()) {
                val firstBook = searchedResponse.results[0]
                Log.d("SEARCH", "🌐 📖 Первая книга: ${firstBook.title}")
            }

            searchedResponse.results

        } catch (e: Exception) {
            Log.e("SEARCH", "🌐 ❌ ОШИБКА Ktor: ${e.message}")
            Log.e("SEARCH", "🌐 Тип ошибки: ${e::class.simpleName}")
            e.printStackTrace()
            emptyList()
        }
    }
}