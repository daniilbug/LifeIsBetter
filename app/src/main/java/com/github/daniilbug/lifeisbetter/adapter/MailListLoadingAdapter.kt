package com.github.daniilbug.lifeisbetter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.daniilbug.lifeisbetter.R

class MailListLoadingAdapter: LoadStateAdapter<MailListLoadingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_loading_mail, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val image: AppCompatImageView = itemView.findViewById(R.id.itemLoadingImage)

        fun bind(loadState: LoadState) {
            when (loadState) {
                is LoadState.Loading -> showLoading()
            }
        }

        private fun showLoading() {
            image.setImageResource(R.drawable.ic_mailbox_loading)
        }
    }
}