package com.kliachenko.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kliachenko.presentation.core.BaseFragment
import com.kliachenko.presentation.core.UpdateUi
import com.kliachenko.presentation.databinding.FragmentSettingsBinding

class SettingsFragment :
    BaseFragment<FragmentSettingsBinding, SettingsViewModel>() {

    override val viewModelClass: Class<SettingsViewModel> = SettingsViewModel::class.java
    private lateinit var observer: UpdateUi<SettingsUiState>

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentSettingsBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer = object : UpdateUi<SettingsUiState> {
            override fun updateUi(uiState: SettingsUiState) {

            }
        }

        viewModel.init()

        binding.saveButton.setOnClickListener {
            viewModel.save()
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