package com.kliachenko.presentation.settings.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kliachenko.presentation.databinding.ChoiceCurrencyBinding
import com.kliachenko.presentation.databinding.EmptySettingsLayoutBinding

interface TypeUi {

    fun viewHolder(parent: ViewGroup, clickActions: (String) -> Unit): SettingsViewHolder

    object Default : TypeUi {
        override fun viewHolder(parent: ViewGroup, clickActions: (String) -> Unit) =
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
        override fun viewHolder(parent: ViewGroup, clickActions: (String) -> Unit) =
            SettingsViewHolder.Empty(
                EmptySettingsLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }
}

