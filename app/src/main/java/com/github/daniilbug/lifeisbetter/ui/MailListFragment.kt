package com.github.daniilbug.lifeisbetter.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import androidx.paging.LoadState
import com.github.daniilbug.lifeisbetter.R
import com.github.daniilbug.lifeisbetter.adapter.MailListAdapter
import com.github.daniilbug.lifeisbetter.adapter.MailListLoadingAdapter
import com.github.daniilbug.lifeisbetter.utils.BaseFragment
import com.github.daniilbug.lifeisbetter.viewmodel.MailView
import com.github.daniilbug.lifeisbetter.viewmodel.maillist.MailListViewModel
import kotlinx.android.synthetic.main.fragment_messages_list.*
import kotlinx.android.synthetic.main.fragment_messages_list.view.*
import kotlinx.android.synthetic.main.fragment_messages_list.view.messagesListProgressBar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class MailListFragment: BaseFragment(R.layout.fragment_messages_list, needBottomNavigation = true) {
    private val viewModel: MailListViewModel by viewModel()

    private lateinit var mailAdapter: MailListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mailAdapter =
            MailListAdapter(onClick = { card, mail ->
                showDetails(
                    card,
                    mail
                )
            })
        view.messagesListRecycler.adapter = mailAdapter.withLoadStateHeaderAndFooter(MailListLoadingAdapter(), MailListLoadingAdapter())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lifecycleScope.launch {
            viewModel.state.collectLatest { mails ->
                mailAdapter.submitData(mails)
            }
        }
        lifecycleScope.launch {
            mailAdapter.loadStateFlow.collectLatest { state ->
                if (state.refresh is LoadState.Loading) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }
        }
    }

    private fun hideLoading() = view?.run {
        messagesListProgressBar.isVisible = false
    }

    private fun showLoading() = view?.run {
        messagesListProgressBar.isVisible = true
    }

    private fun showDetails(card: View, mail: MailView) {
        val extras = FragmentNavigatorExtras(card to mail.id)
        val args = bundleOf("mail" to mail)
        NavHostFragment.findNavController(this).navigate(R.id.showMessageDetails, args, null, extras)
    }
}