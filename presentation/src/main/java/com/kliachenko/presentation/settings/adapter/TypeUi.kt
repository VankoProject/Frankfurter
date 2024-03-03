package com.kliachenko.presentation.settings.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kliachenko.presentation.databinding.ChoiceCurrencyBinding
import com.kliachenko.presentation.databinding.EmptySettingsLayoutBinding
import com.kliachenko.presentation.settings.ChooseCurrency

interface TypeUi {

    fun viewHolder(parent: ViewGroup, clickActions: ChooseCurrency): SettingsViewHolder

    object Currency : TypeUi {
        override fun viewHolder(parent: ViewGroup, clickActions: ChooseCurrency) =
            SettingsViewHolder.Currency(
                ChoiceCurrencyBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                clickActions
            )
    }

    object Empty : TypeUi {
        override fun viewHolder(parent: ViewGroup, clickActions: ChooseCurrency) =
            SettingsViewHolder.Empty(
                EmptySettingsLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }
}

