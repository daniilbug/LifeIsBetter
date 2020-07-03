package com.github.daniilbug.lifeisbetter

import android.os.Bundle
import android.view.View
import com.github.daniilbug.lifeisbetter.utils.BaseFragment
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment: BaseFragment(R.layout.fragment_profile, needBottomNavigation = true) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.profileGoodMessagesCount.text = "218"
        view.profileAllMessagesCount.text = "1823"
    }
}