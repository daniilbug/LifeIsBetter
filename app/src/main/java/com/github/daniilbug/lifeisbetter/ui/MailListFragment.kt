package com.github.daniilbug.lifeisbetter.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.github.daniilbug.lifeisbetter.R
import com.github.daniilbug.lifeisbetter.adapter.MailListAdapter
import com.github.daniilbug.lifeisbetter.utils.BaseFragment
import com.github.daniilbug.lifeisbetter.viewmodel.MailView
import kotlinx.android.synthetic.main.fragment_messages_list.view.*
import java.util.*
import kotlin.random.Random

class MailListFragment: BaseFragment(R.layout.fragment_messages_list, needBottomNavigation = true) {
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
        view.messagesListRecycler.adapter = mailAdapter
        mailAdapter.submitList(List(10) { index -> MailView("$index", "Test message $index", Date(Calendar.getInstance().timeInMillis), Random.nextBoolean()) })
    }

    private fun showDetails(card: View, id: String) {
        val extras = FragmentNavigatorExtras(card to id)
        val args = bundleOf("messageId" to id)
        NavHostFragment.findNavController(this).navigate(R.id.showMessageDetails, args, null, extras)
    }
}