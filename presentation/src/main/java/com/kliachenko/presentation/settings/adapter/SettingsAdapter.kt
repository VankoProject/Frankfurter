package com.kliachenko.presentation.settings.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kliachenko.presentation.core.MainDiffUtil
import com.kliachenko.presentation.core.ShowList
import com.kliachenko.presentation.databinding.ChoiceCurrencyBinding
import com.kliachenko.presentation.databinding.EmptySettingsLayoutBinding

class SettingsAdapter(
    private val clickListener: (String) -> Unit,
    private val types: List<TypeUi> = listOf(TypeUi.Default, TypeUi.Empty),
) : RecyclerView.Adapter<SettingsViewHolder>(), ShowList<CurrencyChoiceUi> {

    private val listCurrency = mutableListOf<CurrencyChoiceUi>()

    override fun getItemViewType(position: Int): Int {
        val type = listCurrency[position].type()
        val index = types.indexOf(type)
        if (index == -1) throw IllegalStateException("Type $type isn't included in the typeList $types")
        return index
    }

    override fun show(list: List<CurrencyChoiceUi>) {
        val diffUtil = MainDiffUtil(oldList = listCurrency, newList = list)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        listCurrency.clear()
        listCurrency.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    fun selectedCurrency(): String {
        val selectedCurrency = listCurrency.find { it.isSelected() }
        return selectedCurrency?.id() ?: ""
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        return types[viewType].viewHolder(parent, clickListener)
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        val currency = listCurrency[position]
        holder.bind(currency)
    }

    override fun getItemCount() = listCurrency.size

}

abstract class SettingsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    open fun bind(currency: CurrencyChoiceUi) = Unit

    class Currency(
        private val binding: ChoiceCurrencyBinding,
        private val clickListener: (String) -> Unit,
    ) : SettingsViewHolder(binding.root) {
        override fun bind(currency: CurrencyChoiceUi) {
            super.bind(currency)
            currency.show(binding.currencyTextView, binding.selectedIconImageView)
            itemView.setOnClickListener {
                clickListener.invoke(currency.id())
            }
        }
    }

    class Empty(binding: EmptySettingsLayoutBinding) : SettingsViewHolder(binding.root)
}