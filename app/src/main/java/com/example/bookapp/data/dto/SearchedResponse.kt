package com.example.bookapp.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchedResponse(
    @SerialName("docs") val results: List<SearchedBookDto>
)
