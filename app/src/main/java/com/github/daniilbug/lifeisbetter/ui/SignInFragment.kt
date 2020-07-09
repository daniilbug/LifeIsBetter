package com.github.daniilbug.lifeisbetter.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.github.daniilbug.lifeisbetter.R
import com.github.daniilbug.lifeisbetter.utils.BaseFragment
import com.github.daniilbug.lifeisbetter.utils.showWithScale
import com.github.daniilbug.lifeisbetter.viewmodel.signin.SignInEvent
import com.github.daniilbug.lifeisbetter.viewmodel.signin.SignInState
import com.github.daniilbug.lifeisbetter.viewmodel.signin.SignInStatus
import com.github.daniilbug.lifeisbetter.viewmodel.signin.SignInViewModel
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_in.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class SignInFragment: BaseFragment(R.layout.fragment_sign_in, needBottomNavigation = false) {
    private val viewModel: SignInViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view) {
            signInButton.setOnClickListener {
                val email = signInEmailEditText.text.toString()
                val password = signInPasswordEditText.text.toString()
                signIn(email, password)
            }
            signInGoToSignUpButton.setOnClickListener {
                openSignUp()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner, Observer { state -> setState(state) })
        viewModel.status.observe(viewLifecycleOwner, Observer { status -> processStatus(status) })
    }

    private fun setState(state: SignInState) {
        when (state) {
            is SignInState.Success -> showMailListScreen()
            is SignInState.Loading -> showLoading()
            is SignInState.Default -> showDefault()
        }
    }

    private fun processStatus(status: SignInStatus) {
        when (status) {
            is SignInStatus.Error -> showError(status.message)
        }
    }

    private fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun showDefault() {
        signInLoading.isVisible = false
        if (!signInButton.isVisible)
            signInButton.showWithScale()
    }

    private fun showLoading() = view?.run {
        signInButton.isVisible = false
        signInLoading.showWithScale()
    }

    private fun showMailListScreen() {
        NavHostFragment.findNavController(this).navigate(R.id.signIn)
    }

    private fun signIn(email: String, password: String) {
        viewModel.sendEvent(SignInEvent.SignIn(email.trim(), password.trim()))
    }

    private fun View.openSignUp() {
        val extras = FragmentNavigatorExtras(
            signInCard to signInCard.transitionName,
            signInTopBackground to signInTopBackground.transitionName
        )
        findNavController().navigate(R.id.openSignUp, null, null, extras)
    }
}