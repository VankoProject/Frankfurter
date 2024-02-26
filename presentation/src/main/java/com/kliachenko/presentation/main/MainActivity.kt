package com.kliachenko.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kliachenko.presentation.R
import com.kliachenko.presentation.core.CustomViewModel
import com.kliachenko.presentation.core.ProvideViewModel
import com.kliachenko.presentation.core.Screen
import com.kliachenko.presentation.core.UpdateUi
import com.kliachenko.presentation.databinding.ActivityMainBinding
import com.kliachenko.presentation.loading.LoadScreen

class MainActivity : AppCompatActivity(), ProvideViewModel {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var navigation: UpdateUi<Screen>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = viewModel(MainViewModel::class.java)
        navigation = object : UpdateUi<Screen> {
            override fun updateUi(uiState: Screen) {
                uiState.showScreen(R.id.container, supportFragmentManager)
            }
        }
        navigation.updateUi(LoadScreen)

    }

    override fun <T : CustomViewModel> viewModel(viewModelClass: Class<T>): T {
        return (application as ProvideViewModel).viewModel(viewModelClass)
    }
}