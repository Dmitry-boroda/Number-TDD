package com.example.number.numbers.presentation

import android.content.Context
import androidx.annotation.StringRes

interface ManagerResources {

    fun string(@StringRes id: Int):String

    class Base(private val context: Context): ManagerResources{
        override fun string(id: Int): String = context.getString(id)

    }
}