package com.kliachenko.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kliachenko.presentation.R
import com.kliachenko.presentation.core.Screen
import com.kliachenko.presentation.core.UpdateUi
import com.kliachenko.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var navigation: UpdateUi<Screen>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navigation = object : UpdateUi<Screen> {
            override fun updateUi(uiState: Screen) {
                uiState.showScreen(R.id.container, supportFragmentManager)
                viewModel.notifyObserved()
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

}