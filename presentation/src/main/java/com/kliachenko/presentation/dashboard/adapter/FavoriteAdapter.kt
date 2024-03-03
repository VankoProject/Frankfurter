package com.kliachenko.presentation.dashboard.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kliachenko.presentation.core.MainDiffUtil
import com.kliachenko.presentation.core.ShowList
import com.kliachenko.presentation.dashboard.ClickActions
import com.kliachenko.presentation.databinding.EmptyLayoutBinding
import com.kliachenko.presentation.databinding.ErrorLayoutBinding
import com.kliachenko.presentation.databinding.FavoritePairLayoutBinding
import com.kliachenko.presentation.databinding.ProgressLayoutBinding

class FavoriteAdapter(
    private val clickListener: ClickActions,
    private val types: List<TypeUi> = listOf(
        TypeUi.Empty,
        TypeUi.Error,
        TypeUi.Progress,
        TypeUi.FavoritePair
    ),
) : RecyclerView.Adapter<DashboardViewHolder>(), ShowList<FavoritePairUi> {

    private val listPairs = mutableListOf<FavoritePairUi>()

    override fun getItemViewType(position: Int): Int {
        val type = listPairs[position].type()
        val index = types.indexOf(type)
        if (index == -1)
            throw IllegalStateException("Type $type isn't included in the typeList $types")
        return index
    }

    override fun show(list: List<FavoritePairUi>) {
        val diffUtil =
            MainDiffUtil(oldList = listPairs, newList = list, itemId = { it.id() })
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        listPairs.clear()
        listPairs.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        return types[viewType].viewHolder(parent, clickListener)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        holder.bind(listPairs[position])
    }

    override fun getItemCount() = listPairs.size

}

abstract class DashboardViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    open fun bind(pair: FavoritePairUi) = Unit

    class Progress(binding: ProgressLayoutBinding) : DashboardViewHolder(binding.root)

    class Error(private val binding: ErrorLayoutBinding, private val clickListener: ClickActions) :
        DashboardViewHolder(binding.root) {

        override fun bind(pair: FavoritePairUi) {
            pair.show(binding)
            binding.retryButton.setOnClickListener { clickListener.retry() }
        }
    }

    class CurrencyPair(private val binding: FavoritePairLayoutBinding) :
        DashboardViewHolder(binding.root) {
        override fun bind(pair: FavoritePairUi) {
            pair.show(binding)
        }
    }

    class Empty(binding: EmptyLayoutBinding) : DashboardViewHolder(binding.root)
}