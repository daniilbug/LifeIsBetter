package com.github.daniilbug.lifeisbetter

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import com.github.daniilbug.lifeisbetter.utils.BaseFragment
import com.github.daniilbug.lifeisbetter.utils.EmptyTransitionListener
import kotlinx.android.synthetic.main.fragment_write_mail.*
import kotlinx.android.synthetic.main.fragment_write_mail.view.*

class WriteMailFragment : BaseFragment(R.layout.fragment_write_mail, needBottomNavigation = true) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.writeMailMotionLayout.setTransitionListener(object : EmptyTransitionListener() {
            override fun onTransitionCompleted(layour: MotionLayout?, state: Int) {
                if (state == R.id.sentState) {
                    onSentState(view)
                }
            }
        })
    }


    private fun onSentState(view: View) {
        writeMailText.text?.clear()
    }
}