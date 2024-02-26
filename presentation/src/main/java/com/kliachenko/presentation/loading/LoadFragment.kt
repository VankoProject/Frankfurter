package com.kliachenko.presentation.loading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kliachenko.presentation.core.BaseFragment
import com.kliachenko.presentation.core.UpdateUi
import com.kliachenko.presentation.databinding.FragmentLoadBinding

class LoadFragment : BaseFragment<FragmentLoadBinding>() {

    private lateinit var viewModel: LoadViewModel
    private lateinit var updateUi: UpdateUi<LoadUiState>

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?): FragmentLoadBinding {
        return FragmentLoadBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUi = object : UpdateUi<LoadUiState> {
            override fun updateUi(uiState: LoadUiState) {
                uiState.update(
                    retryButton = binding.retryButton,
                    progressBar = binding.progressBar,
                    textView = binding.errorTextView
                )
            }
        }

        binding.retryButton.setOnClickListener {
            viewModel.retry()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.startGettingUpdates(updateUi)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopGettingUpdates()
    }
}