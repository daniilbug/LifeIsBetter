package com.github.daniilbug.lifeisbetter.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.github.daniilbug.lifeisbetter.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpBottomNavigation()
    }

    fun hideBottomNavigation() {
        mainBottomNavigation.isVisible = false
    }

    fun showBottomNavigation() {
        mainBottomNavigation.isVisible = true
    }

    private fun setUpBottomNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as? NavHostFragment ?: return
        mainBottomNavigation.setupWithNavController(navHostFragment.navController)
    }
}