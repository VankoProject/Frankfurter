package com.kliachenko.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.kliachenko.presentation.core.BaseFragment
import com.kliachenko.presentation.core.UpdateUi
import com.kliachenko.presentation.databinding.FragmentSettingsBinding
import com.kliachenko.presentation.settings.adapter.SettingsAdapter

class SettingsFragment :
    BaseFragment<FragmentSettingsBinding, SettingsViewModel>() {

    override val viewModelClass: Class<SettingsViewModel> = SettingsViewModel::class.java
    private lateinit var observer: UpdateUi<SettingsUiState>

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentSettingsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fromCurrencyAdapter =
            SettingsAdapter({ fromCurrency -> viewModel.chooseFirstCurrency(fromCurrency) })
        val toCurrencyAdapter =
            SettingsAdapter({ toCurrency ->
                viewModel.chooseSecondCurrency(
                    fromCurrencyAdapter.selectedCurrency(),
                    toCurrency
                )
            })

        with(binding) {
            recycleViewFrom.adapter = fromCurrencyAdapter
            recycleViewTo.adapter = toCurrencyAdapter
        }

        observer = object : UpdateUi<SettingsUiState> {
            override fun updateUi(uiState: SettingsUiState) {
                uiState.update(
                    fromCurrencyAdapter = fromCurrencyAdapter,
                    toCurrencyAdapter = toCurrencyAdapter
                )
                uiState.update(binding.saveButton)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.backDashBoard()
                }
            })

        viewModel.init()

        binding.saveButton.setOnClickListener {
            val selectedCurrencyFrom = fromCurrencyAdapter.selectedCurrency()
            val selectedCurrencyTo = toCurrencyAdapter.selectedCurrency()
            viewModel.save(selectedCurrencyFrom, selectedCurrencyTo)
        }

        binding.backButton.setOnClickListener {
            viewModel.backDashBoard()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.startGettingUpdates(observer)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopGettingUpdates()
    }

}