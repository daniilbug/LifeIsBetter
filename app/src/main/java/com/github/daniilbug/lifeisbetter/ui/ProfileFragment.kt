package com.github.daniilbug.lifeisbetter.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.github.daniilbug.lifeisbetter.R
import com.github.daniilbug.lifeisbetter.utils.BaseFragment
import com.github.daniilbug.lifeisbetter.utils.animateFadeIn
import com.github.daniilbug.lifeisbetter.viewmodel.profile.ProfileEvent
import com.github.daniilbug.lifeisbetter.viewmodel.profile.ProfileState
import com.github.daniilbug.lifeisbetter.viewmodel.profile.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment: BaseFragment(R.layout.fragment_profile, needBottomNavigation = true) {

    private val viewModel: ProfileViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view) {
            profileLogOutButton.setOnClickListener {
                viewModel.sendEvent(ProfileEvent.LogOut)
            }
            profileSwipeRefresh.setColorSchemeResources(R.color.colorOrangeDark, R.color.colorOrangeDark, R.color.colorOrangeDark)
            profileSwipeRefresh.setOnRefreshListener { viewModel.sendEvent(ProfileEvent.Refresh) }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner, Observer { state -> setState(state) })
    }

    private fun setState(state: ProfileState) {
        when (state) {
            is ProfileState.LogOut -> logOut()
            is ProfileState.Statistic -> showStatistic(state.mailsAmount, state.goodMailsAmount)
        }
    }

    private fun showStatistic(mailsAmount: Int, goodMailsAmount: Int) = view?.run {
        profileGoodMessagesCount.text = goodMailsAmount.toString()
        profileAllMessagesCount.text = mailsAmount.toString()
        profileGoodMessagesCount.animateFadeIn()
        profileAllMessagesCount.animateFadeIn()
        profileSwipeRefresh.isRefreshing = false
    }

    private fun logOut() {
        findNavController().navigate(R.id.logOut)
    }
}