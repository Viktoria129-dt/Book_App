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

            try {
                val books = repository.searchBooks(query)

                _state.update {
                    it.copy(
                        isLoading = false,
                        searchResults = books
                    )
                }

            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = UiText.DynamicString(
                            e.message ?: "Download error"
                        )
                    )
                }
            }
        }
    }

}