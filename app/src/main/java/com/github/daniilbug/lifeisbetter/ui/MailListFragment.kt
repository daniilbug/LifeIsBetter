package com.github.daniilbug.lifeisbetter.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import com.github.daniilbug.lifeisbetter.R
import com.github.daniilbug.lifeisbetter.adapter.MailListAdapter
import com.github.daniilbug.lifeisbetter.adapter.MailListLoadingAdapter
import com.github.daniilbug.lifeisbetter.utils.BaseFragment
import com.github.daniilbug.lifeisbetter.viewmodel.maillist.MailListViewModel
import kotlinx.android.synthetic.main.fragment_messages_list.view.*
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
            MailListAdapter(onClick = { card, id ->
                showDetails(
                    card,
                    id
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
    }

    private fun showDetails(card: View, id: String) {
        val extras = FragmentNavigatorExtras(card to id)
        val args = bundleOf("messageId" to id)
        NavHostFragment.findNavController(this).navigate(R.id.showMessageDetails, args, null, extras)
    }
}