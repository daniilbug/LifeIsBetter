package com.github.daniilbug.lifeisbetter

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpNavigation()
    }

    fun hideBottomNavigation() {
        mainBottomNavigation.isVisible = false
    }

    fun showBottomNavigation() {
        mainBottomNavigation.isVisible = true
    }

    private fun setUpNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as? NavHostFragment ?: return
        NavigationUI.setupWithNavController(mainBottomNavigation, navHostFragment.navController)
    }
}