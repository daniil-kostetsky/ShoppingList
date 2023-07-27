package com.example.shoppinglist.presentation.adapters

import androidx.databinding.BindingAdapter
import com.example.shoppinglist.R
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorInputName")
fun bindErrorInputName(textInputLayout: TextInputLayout, errorInputName: Boolean) {
    val message = if (errorInputName) {
        textInputLayout.context.getString(R.string.invalid_input_name)
    } else {
        null
    }
    textInputLayout.error = message
}

@BindingAdapter("errorInputCount")
fun bindErrorInputCount(textInputLayout: TextInputLayout, errorInputCount: Boolean) {
    val message = if (errorInputCount) {
        textInputLayout.context.getString(R.string.invalid_input_count)
    } else {
        null
    }
    textInputLayout.error = message
}