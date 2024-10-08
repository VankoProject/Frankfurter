package com.kliachenko.presentation.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.kliachenko.presentation.core.BaseFragment
import com.kliachenko.presentation.core.UpdateUi
import com.kliachenko.presentation.dashboard.adapter.FavoriteAdapter
import com.kliachenko.presentation.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashBoardFragment : BaseFragment<FragmentDashboardBinding>() {

    private lateinit var updateUi: UpdateUi<DashboardUiState>
    private val viewModel: DashBoardViewModel by viewModels()

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentDashboardBinding {
        return FragmentDashboardBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = FavoriteAdapter(viewModel)
        binding.dashboardRecycleView.adapter = adapter

        updateUi = object : UpdateUi<DashboardUiState> {
            override fun updateUi(uiState: DashboardUiState) {
                uiState.update(adapter)
            }
        }

        viewModel.load()

        binding.settingsButton.setOnClickListener {
            viewModel.openSettings()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.startGettingUpdates(observer = updateUi)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopGettingUpdates()
    }

}