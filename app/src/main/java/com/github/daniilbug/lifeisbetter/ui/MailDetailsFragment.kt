package com.github.daniilbug.lifeisbetter.ui

import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.github.daniilbug.lifeisbetter.R
import com.github.daniilbug.lifeisbetter.utils.BaseFragment
import com.github.daniilbug.lifeisbetter.viewmodel.MailFeedBack
import com.github.daniilbug.lifeisbetter.viewmodel.MailView
import com.github.daniilbug.lifeisbetter.viewmodel.maildetails.MailDetailsEvent
import com.github.daniilbug.lifeisbetter.viewmodel.maildetails.MailDetailsState
import com.github.daniilbug.lifeisbetter.viewmodel.maildetails.MailDetailsViewModel
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.fragment_message_details.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

@FlowPreview
@ExperimentalCoroutinesApi
class MailDetailsFragment : BaseFragment(R.layout.fragment_message_details, needBottomNavigation = false) {

    companion object {
        val MAIL_ARG_ERROR = "There must be mail argument for ${MailDetailsFragment::class.simpleName}"
    }

    private val viewModel: MailDetailsViewModel by viewModel {
        parametersOf(
            arguments?.getParcelable("mail") ?: error(MAIL_ARG_ERROR)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mailView = arguments?.getParcelable<MailView>("mail")
        checkNotNull(mailView) { MAIL_ARG_ERROR }
        with(view) {
            transitionName = mailView.id
            sharedElementEnterTransition = MaterialContainerTransform().apply {
                endView = messageDetailsLayout
                duration = 300
            }
            exitTransition = Hold()
            messageDetailsText.text = mailView.text
            messageDetailsDate.text = DateFormat.getDateFormat(context).format(mailView.date)
            messageDetailsBackButton.setOnClickListener {
                findNavController().popBackStack()
            }
            showResultButtonsWithAnimations(this)
            setRadioButtonListeners()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner, Observer { state -> setState(state) })
    }

    private fun setState(state: MailDetailsState) {
        when (state) {
            is MailDetailsState.Loading -> {}
            is MailDetailsState.FeedBack -> view?.setCheckedByFeedback(state.feedBack)
        }
    }

    private fun View.setRadioButtonListeners() {
        fun postFeedback(feedBack: MailFeedBack) {
            viewModel.sendEvent(MailDetailsEvent.ChangeFeedback(feedBack))
        }
        messageDetailsGoodButton.setOnClickListener { postFeedback(MailFeedBack.GOOD) }
        messageDetailsNeutralButton.setOnClickListener { postFeedback(MailFeedBack.NEUTRAL) }
        messageDetailsBadButton.setOnClickListener { postFeedback(MailFeedBack.BAD) }
    }

    private fun View.setCheckedByFeedback(feedBack: MailFeedBack) {
        when (feedBack) {
            MailFeedBack.NONE -> {}
            MailFeedBack.GOOD -> messageDetailsGoodButton.isChecked = true
            MailFeedBack.NEUTRAL -> messageDetailsNeutralButton.isChecked = true
            MailFeedBack.BAD -> messageDetailsBadButton.isChecked = true
        }
    }

    private fun showResultButtonsWithAnimations(view: View) {
        fun View.show() {
            animate().scaleX(1f).scaleY(1f).setDuration(300).setStartDelay(200).start()
        }
        with(view) {
            messageDetailsBadButton.show()
            messageDetailsNeutralButton.show()
            messageDetailsGoodButton.show()
        }
    }

    private fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}