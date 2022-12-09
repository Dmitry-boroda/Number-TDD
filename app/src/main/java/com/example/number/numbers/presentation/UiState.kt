package com.example.number.numbers.presentation

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

sealed class UiState {

    abstract fun apply(inputLayout: TextInputLayout, textInputEditText: TextInputEditText)

    class Success : UiState() {
        override fun apply(inputLayout: TextInputLayout, textInputEditText: TextInputEditText) {
            textInputEditText.setText("")
        }
    }

    abstract class AbstractError(
        private val message: String,
        private val errorEnable: Boolean
    ) : UiState() {
        override fun apply(
            inputLayout: TextInputLayout,
            textInputEditText: TextInputEditText
        ) = with(inputLayout) {
            isErrorEnabled = errorEnable
            error = message
        }
    }

    data class ShowError(private val message: String) : AbstractError(message, true)

    class ClearError : AbstractError("", false)

}
