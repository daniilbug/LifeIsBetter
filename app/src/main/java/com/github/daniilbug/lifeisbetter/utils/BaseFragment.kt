package com.github.daniilbug.lifeisbetter.utils

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.github.daniilbug.lifeisbetter.ui.MainActivity

abstract class BaseFragment(@LayoutRes layoutId: Int, private val needBottomNavigation: Boolean): Fragment(layoutId) {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val activity = activity as? MainActivity
            ?: return
        if (needBottomNavigation)
            activity.showBottomNavigation()
        else
            activity.hideBottomNavigation()
        super.onActivityCreated(savedInstanceState)
    }
}