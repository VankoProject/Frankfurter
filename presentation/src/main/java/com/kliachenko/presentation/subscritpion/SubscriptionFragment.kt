package com.kliachenko.presentation.subscritpion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.kliachenko.presentation.core.BaseFragment
import com.kliachenko.presentation.databinding.FragmentSubscriptionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubscriptionFragment : BaseFragment<FragmentSubscriptionBinding>() {

    private val viewModel: SubscriptionViewModel by viewModels()

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentSubscriptionBinding {
        return FragmentSubscriptionBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.goSettingScreen()
                }
            })

        binding.subscriptionBackButton.setOnClickListener {
            viewModel.goSettingScreen()
        }

        binding.savePremiumButton.setOnClickListener {
            viewModel.buyPremium()
        }
    }

}