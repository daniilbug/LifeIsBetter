package com.github.daniilbug.lifeisbetter

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import com.github.daniilbug.lifeisbetter.utils.BaseFragment
import kotlinx.android.synthetic.main.fragment_sign_in.view.*

class SignInFragment: BaseFragment(R.layout.fragment_sign_in, needBottomNavigation = false) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view) {
            signInButton.setOnClickListener {
                signIn()
            }
            signInGoToSignUpButton.setOnClickListener {
                openSignUp()
            }
        }
    }

    private fun signIn() {
        NavHostFragment.findNavController(this).navigate(R.id.signIn)
    }

    private fun View.openSignUp() {
        val extras = FragmentNavigatorExtras(
            signInCard to signInCard.transitionName,
            signInTopBackground to signInTopBackground.transitionName
        )
        val navigator = NavHostFragment.findNavController(this@SignInFragment)
        navigator.navigate(R.id.openSignUp, null, null, extras)
    }
}