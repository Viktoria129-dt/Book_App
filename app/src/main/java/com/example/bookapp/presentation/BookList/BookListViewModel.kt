package com.example.bookapp.presentation.BookList

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.lifecycle.viewModelScope
import com.example.bookapp.core.presentation.UiText
import com.example.bookapp.data.repositories.Repository
import com.example.bookapp.domain.Book
import kotlinx.coroutines.launch

class BookListViewModel(private val repository: Repository): ViewModel() {
    private val _state = MutableStateFlow(BookListState())
    val state = _state.asStateFlow()
    fun onAction(action: BookListAction){
       when(action){
           is BookListAction.OnBookClick -> {

           }
           is BookListAction.OnSearchQueryChange -> {
               _state.update {
                   it.copy(searchQuery = action.query,)
               }
           }
           is BookListAction.OnTabSelected -> {
               _state.update {
                   it.copy(selectedTabIndex = action.index,)
               }
           }
           is BookListAction.OnSearchButtonClick ->
               searchBooks()
       }
    }
    private fun searchBooks(){
        val query = _state.value.searchQuery.trim()

        if (query.isEmpty()){
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true,) }

            try{

                val dtoBooks = repository.searchBooks(query)

                val domainBooks = dtoBooks.map { dto ->
                    Book(
                        id = dto.id,
                        title = dto.title,
                        authors = dto.authorsNames ?: emptyList(),
                        imageUrl = if (dto.coverAlternativeKey != null) {
                            "https://covers.openlibrary.org/b/id/${dto.coverAlternativeKey}-M.jpg"
                        } else {
                            null
                        },
                        description = null,
                        languages = dto.language,
                        firstPublishYear = dto.firstPublishYear,
                        averageRating = dto.ratingsAverage,
                        ratingCount = dto.ratingCount,
                        numPages = dto.numPagesMedian,
                        numEditions = dto.numEdition
                    )
                }

                _state.update {
                    it.copy(
                        isLoading = false,
                        searchResults = domainBooks
                    )
                }

            }
            catch (e: Exception){
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = UiText.DynamicString("Error: ${e.message}")
                    )
                }
            }
        }
    }

}