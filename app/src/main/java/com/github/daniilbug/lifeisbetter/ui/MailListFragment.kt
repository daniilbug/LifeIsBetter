package com.github.daniilbug.lifeisbetter.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
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
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.fragment_messages_list.view.*
import kotlinx.android.synthetic.main.fragment_messages_list.view.messagesListProgressBar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class MailListFragment :
    BaseFragment(R.layout.fragment_messages_list, needBottomNavigation = true) {
    private val viewModel: MailListViewModel by viewModel()

    private lateinit var mailAdapter: MailListAdapter
    private var errorView: View? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mailAdapter =
            MailListAdapter(onClick = { card, mail ->
                showDetails(
                    card,
                    mail
                )
            })
        view.messagesListRecycler.adapter = mailAdapter.withLoadStateHeaderAndFooter(
            MailListLoadingAdapter(),
            MailListLoadingAdapter()
        )
        with(view.messagesListSwipeRefresh) {
            setColorSchemeResources(
                R.color.colorOrangeDark,
                R.color.colorOrangeDark,
                R.color.colorOrangeDark
            )
            setOnRefreshListener { mailAdapter.refresh() }
        }
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
                val refreshState = state.refresh
                if (refreshState is LoadState.Error) {
                    processError(refreshState.error)
                }
                if (refreshState !is LoadState.Loading) {
                    hideLoading()
                    view?.messagesListSwipeRefresh?.isRefreshing = false
                }
            }
        }
    }

    private fun processError(error: Throwable) {
        when (error) {
            is MailListViewModel.EmptyListException -> showEmptyList()
        }
    }

    private fun showEmptyList() = view?.run {
        val errorView = errorView ?: messagesListViewStub.inflate()
        this@MailListFragment.errorView = errorView
        errorView.apply {
            findViewById<AppCompatImageView>(R.id.emptyListImage).apply {
                setImageResource(R.drawable.ic_mailbox_one_color)
                alpha = 0f
                animate().alpha(1f).setDuration(300L).start()
            }
            findViewById<MaterialTextView>(R.id.emptyListText).apply {
                setText(R.string.empty_mailbox)
                alpha = 0f
                animate().alpha(1f).setDuration(300L).start()
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
        NavHostFragment.findNavController(this)
            .navigate(R.id.showMessageDetails, args, null, extras)
    }
}