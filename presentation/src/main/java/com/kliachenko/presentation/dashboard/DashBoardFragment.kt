package com.kliachenko.presentation.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kliachenko.presentation.core.BaseFragment
import com.kliachenko.presentation.databinding.FragmentDashboardBinding

class DashBoardFragment : BaseFragment<FragmentDashboardBinding>() {

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentDashboardBinding {
        return FragmentDashboardBinding.inflate(inflater, container, false)

    }

}