package com.kliachenko.presentation.settings

import android.os.Bundle

interface BundleWrapper {

    interface Save {

        fun save(fromCurrency: String, toCurrency: String)
    }

    interface Restore {

        fun restore(): Pair<String, String>
    }

    interface Empty {

        fun isEmpty(): Boolean
    }

    interface Mutable : Save, Restore, Empty

    class Base(private val bundle: Bundle?) : Mutable {

        override fun save(fromCurrency: String, toCurrency: String) {
            bundle!!.apply {
                putString(KEY_FROM_CURRENCY, fromCurrency)
                putString(KEY_TO_CURRENCY, toCurrency)
            }
        }

        override fun restore(): Pair<String, String> = bundle!!.run {
            Pair(getString(KEY_FROM_CURRENCY) ?: "", getString(KEY_TO_CURRENCY) ?: "")
        }

        override fun isEmpty(): Boolean = bundle == null

        companion object {
            private const val KEY_FROM_CURRENCY = "key_from_currency"
            private const val KEY_TO_CURRENCY = "key_to_currency"
        }

    }
}