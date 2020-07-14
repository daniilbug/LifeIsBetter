package com.github.daniilbug.lifeisbetter.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.github.daniilbug.lifeisbetter.R
import com.github.daniilbug.lifeisbetter.utils.BaseFragment
import com.github.daniilbug.lifeisbetter.utils.showWithScale
import com.github.daniilbug.lifeisbetter.viewmodel.signup.SignUpEvent
import com.github.daniilbug.lifeisbetter.viewmodel.signup.SignUpState
import com.github.daniilbug.lifeisbetter.viewmodel.signup.SignUpStatus
import com.github.daniilbug.lifeisbetter.viewmodel.signup.SignUpViewModel
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.signUpButton
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment: BaseFragment(R.layout.fragment_sign_up, needBottomNavigation = false) {
    private val viewModel: SignUpViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view) {
            setupEnterTransition()
            signUpBackButton.setOnClickListener {
                onBack()
            }
            signUpButton.setOnClickListener {
                signUp()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner, Observer { state -> setState(state) })
        viewModel.status.observe(viewLifecycleOwner, Observer { status -> processStatus(status) })
    }

    private fun View.setupEnterTransition() {
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 300
            setPathMotion(MaterialArcMotion())
            scrimColor = Color.TRANSPARENT
        }
        exitTransition = Hold()
    }

    private fun View.signUp() {
        val email = signUpEmailEditText.text.toString()
        val password = signUpPasswordEditText.text.toString()
        val confirmPassword = signUpConfirmPasswordEditText.text.toString()
        viewModel.sendEvent(SignUpEvent.SignUp(email, password, confirmPassword))
    }

    private fun processStatus(status: SignUpStatus) {
        when (status) {
            is SignUpStatus.Error -> showError(status.message)
        }
    }

    private fun setState(state: SignUpState) {
        when (state) {
            is SignUpState.Success -> onBack()
            is SignUpState.Default -> showDefault()
            is SignUpState.Loading -> showLoading()
        }
    }

    private fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun showDefault() {
        signUpLoading.isVisible = false
        if (!signUpButton.isVisible)
            signUpButton.showWithScale()
    }

    private fun showLoading() = view?.run {
        signUpButton.isVisible = false
        signUpLoading.showWithScale()
    }

    private fun onBack() {
        findNavController().popBackStack()
    }
}