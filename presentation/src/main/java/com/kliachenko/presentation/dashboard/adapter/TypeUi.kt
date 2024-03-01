package com.kliachenko.presentation.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kliachenko.presentation.dashboard.ClickActions
import com.kliachenko.presentation.databinding.EmptyLayoutBinding
import com.kliachenko.presentation.databinding.ErrorLayoutBinding
import com.kliachenko.presentation.databinding.FavoritePairLayoutBinding
import com.kliachenko.presentation.databinding.ProgressLayoutBinding

interface TypeUi {

    fun viewHolder(parent: ViewGroup, clickActions: ClickActions): DashboardViewHolder

    object Progress : TypeUi {
        override fun viewHolder(parent: ViewGroup, clickActions: ClickActions) =
            DashboardViewHolder.Progress(
            ProgressLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    object Error : TypeUi {
        override fun viewHolder(parent: ViewGroup, clickActions: ClickActions) =
            DashboardViewHolder.Error(
            ErrorLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false),
            clickListener = clickActions
        )
    }

    object Empty : TypeUi {
        override fun viewHolder(parent: ViewGroup, clickActions: ClickActions) =
            DashboardViewHolder.Empty(
            EmptyLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        )
    }

    object FavoritePair : TypeUi {
        override fun viewHolder(parent: ViewGroup, clickActions: ClickActions) =
            DashboardViewHolder.CurrencyPair(
            FavoritePairLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        )
    }
}