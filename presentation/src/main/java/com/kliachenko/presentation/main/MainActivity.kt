package com.kliachenko.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kliachenko.presentation.R
import com.kliachenko.presentation.core.CustomViewModel
import com.kliachenko.presentation.core.ProvideViewModel
import com.kliachenko.presentation.core.Screen
import com.kliachenko.presentation.core.UpdateUi
import com.kliachenko.presentation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ProvideViewModel {

    private lateinit var viewModel: MainViewModel
    private lateinit var navigation: UpdateUi<Screen>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = viewModel(MainViewModel::class.java)
        navigation = object : UpdateUi<Screen> {
            override fun updateUi(uiState: Screen) {
                uiState.showScreen(R.id.container, supportFragmentManager)
            }
        }
        viewModel.init(savedInstanceState == null)
    }

    override fun onResume() {
        super.onResume()
        viewModel.startGettingUpdates(navigation)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopGettingUpdates()
    }

    override fun <T : CustomViewModel> viewModel(viewModelClass: Class<T>): T {
        return (application as ProvideViewModel).viewModel(viewModelClass)
    }
}