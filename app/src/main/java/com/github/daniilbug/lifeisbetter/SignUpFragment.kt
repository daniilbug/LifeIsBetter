package com.github.daniilbug.lifeisbetter

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import com.github.daniilbug.lifeisbetter.utils.BaseFragment
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.fragment_sign_up.view.*

class SignUpFragment: BaseFragment(R.layout.fragment_sign_up, needBottomNavigation = false) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view) {
            signUpBackButton.setOnClickListener {
                onBack()
            }
            sharedElementEnterTransition = MaterialContainerTransform().apply {
                duration = 300
                setPathMotion(MaterialArcMotion())
                scrimColor = Color.TRANSPARENT
            }
            exitTransition = Hold()
        }
    }

    private fun onBack() {
        NavHostFragment.findNavController(this).popBackStack()
    }
}