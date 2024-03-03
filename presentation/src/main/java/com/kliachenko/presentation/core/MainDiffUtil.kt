package com.kliachenko.presentation.core

import androidx.recyclerview.widget.DiffUtil

class MainDiffUtil<T : Any>(
    private val oldList: List<T>,
    private val newList: List<T>,
    private val itemId: (item: T) -> Any,

) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        itemId(oldList[oldItemPosition]) == itemId(newList[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]

}