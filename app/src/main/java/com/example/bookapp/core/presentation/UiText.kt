package com.example.bookapp.core.presentation

import android.R.attr.id
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed interface UiText {
    data class DynamicString(val value: String): UiText
    class StringResourсeId(
        @StringRes val id: Int,
        val args: Array<Any> = arrayOf()
    ): UiText

    @Composable
    fun asString(): String {
        return when(this){
            is DynamicString -> value
            is StringResourсeId -> stringResource(id = id, *args)
        }
    }
}