package com.kliachenko.presentation.subscritpion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.kliachenko.presentation.core.BaseFragment
import com.kliachenko.presentation.databinding.FragmentSubscriptionBinding

class SubscriptionFragment : BaseFragment<FragmentSubscriptionBinding, SubscriptionViewModel>() {

    override val viewModelClass = SubscriptionViewModel::class.java

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

        binding.backButton.setOnClickListener {
            viewModel.goSettingScreen()
        }

        binding.savePremiumButton.setOnClickListener {
            viewModel.buyPremium()
        }
    }

}