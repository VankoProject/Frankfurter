package com.kliachenko.presentation.dashboard.adapter

import com.kliachenko.presentation.core.ListItem
import com.kliachenko.presentation.databinding.ErrorLayoutBinding
import com.kliachenko.presentation.databinding.FavoritePairLayoutBinding

interface FavoritePairUi: ListItem {

    fun type(): TypeUi

    fun show(binding: FavoritePairLayoutBinding) = Unit

    fun show(binding: ErrorLayoutBinding) = Unit

    data class Base(
        private val pair: String,
        private val rate: String,
    ) : FavoritePairUi {
        override fun id() = pair

        override fun type() = TypeUi.FavoritePair

        override fun show(binding: FavoritePairLayoutBinding) = with(binding) {
            currencyPairTextView.text = pair
            rateTextView.text = rate
        }
    }

    data class Error(private val message: String) : FavoritePairUi {
        override fun id() = "error $message"

        override fun type() = TypeUi.Error

        override fun show(binding: ErrorLayoutBinding) {
            binding.errorDashboardTextView.text = message
        }
    }

    object Progress : FavoritePairUi {
        override fun id() = "progress"

        override fun type() = TypeUi.Progress
    }

    object Empty : FavoritePairUi {
        override fun id() = "empty"

        override fun type() = TypeUi.Empty
    }
}


