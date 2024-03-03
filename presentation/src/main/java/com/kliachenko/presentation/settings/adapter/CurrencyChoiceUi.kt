package com.kliachenko.presentation.settings.adapter

import com.kliachenko.presentation.core.views.ChangeText
import com.kliachenko.presentation.core.views.ChangeVisibility
import com.kliachenko.presentation.databinding.EmptySettingsLayoutBinding

interface CurrencyChoiceUi {

    fun id(): String = ""

    fun type(): TypeUi

    fun isSelected(): Boolean = false

    fun show(currencyTextView: ChangeText, selectedImageView: ChangeVisibility) = Unit

    fun show(binding: EmptySettingsLayoutBinding) = Unit

    data class Base(private val isSelected: Boolean, private val currency: String) :
        CurrencyChoiceUi {

        override fun id() = currency

        override fun type() = TypeUi.Currency

        override fun isSelected() = isSelected

    }

    object Empty : CurrencyChoiceUi {

        override fun id() = "empty"

        override fun type() = TypeUi.Empty
    }
}