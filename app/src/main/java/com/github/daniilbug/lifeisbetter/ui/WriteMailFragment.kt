package com.github.daniilbug.lifeisbetter.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.Observer
import com.github.daniilbug.lifeisbetter.R
import com.github.daniilbug.lifeisbetter.utils.BaseFragment
import com.github.daniilbug.lifeisbetter.utils.EmptyTransitionListener
import com.github.daniilbug.lifeisbetter.viewmodel.writemail.WriteMailEvent
import com.github.daniilbug.lifeisbetter.viewmodel.writemail.WriteMailStatus
import com.github.daniilbug.lifeisbetter.viewmodel.writemail.WriteMailViewModel
import kotlinx.android.synthetic.main.fragment_write_mail.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class WriteMailFragment : BaseFragment(R.layout.fragment_write_mail, needBottomNavigation = true) {
    private val viewModel: WriteMailViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view) {
            writeMailMotionLayout.setTransitionListener(object : EmptyTransitionListener() {
                override fun onTransitionCompleted(layour: MotionLayout?, state: Int) {
                    setTransitionState(view, state)
                }
            })
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.status.observe(viewLifecycleOwner, Observer { status -> processStatus(status) })
    }

    private fun setTransitionState(view: View, state: Int) = view.run {
        when (state) {
            R.id.sentState -> onSentState(view)
            R.id.defaultState -> onDefaultState(view)
        }
    }

    private fun processStatus(status: WriteMailStatus) {
        when (status) {
            is WriteMailStatus.Success -> showSuccess()
            is WriteMailStatus.Error -> showError(status.message)
        }
    }

    private fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun showSuccess() {
        Toast.makeText(context, R.string.success, Toast.LENGTH_LONG).show()
    }

    private fun onSentState(view: View) {
        val text = view.writeMailText.text.toString()
        viewModel.sendEvent(WriteMailEvent.SendMail(text))
        view.writeMailText.text?.clear()
    }

    private fun onDefaultState(view: View) {
        view.writeMailText.isEnabled = true
    }
}