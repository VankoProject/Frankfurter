package com.kliachenko.presentation.loading

import android.view.LayoutInflater
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

    override fun onResume() {
        super.onResume()
        viewModel.startGettingUpdates(updateUi)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopGettingUpdates()
    }
}