package com.kliachenko.presentation.core


interface Delimiter {

    fun split(item: String): List<String>

    fun concat(from: String, to: String): String


    class Base(private val value: String = "/"): Delimiter {

        override fun split(item: String): List<String> {
            return item.split(value)
        }

        override fun concat(from: String, to: String): String {
            return from + value + to
        }

    }
}