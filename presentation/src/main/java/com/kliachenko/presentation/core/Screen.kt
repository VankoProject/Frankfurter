package com.kliachenko.presentation.core

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface Screen {

    fun showScreen(container: Int, supportFragmentManager: FragmentManager)

    abstract class Replace(private val clazz: Class<out Fragment>) : Screen {

        override fun showScreen(container: Int, supportFragmentManager: FragmentManager) {
            supportFragmentManager.beginTransaction()
                .replace(container, clazz.getDeclaredConstructor().newInstance())
                .commit()
        }
    }

    object Empty : Screen {
        override fun showScreen(container: Int, supportFragmentManager: FragmentManager) = Unit
    }
}