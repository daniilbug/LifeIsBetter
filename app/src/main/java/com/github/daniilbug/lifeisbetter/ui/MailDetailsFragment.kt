package com.github.daniilbug.lifeisbetter.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import com.github.daniilbug.lifeisbetter.R
import com.github.daniilbug.lifeisbetter.utils.BaseFragment
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.fragment_message_details.view.*

class MailDetailsFragment : BaseFragment(R.layout.fragment_message_details, needBottomNavigation = false) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view) {
            transitionName = arguments?.getString("messageId")
            sharedElementEnterTransition = MaterialContainerTransform().apply {
                endView = messageDetailsLayout
                duration = 300
                setPathMotion(MaterialArcMotion())
            }
            exitTransition = Hold()
            messageDetailsText.text = "Test text".repeat(2)
            messageDetailsDate.text = "13.05.1998"
            messageDetailsBackButton.setOnClickListener {
                NavHostFragment.findNavController(this@MailDetailsFragment).popBackStack()
            }
            showResultButtonsWithAnimations(this)
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
}