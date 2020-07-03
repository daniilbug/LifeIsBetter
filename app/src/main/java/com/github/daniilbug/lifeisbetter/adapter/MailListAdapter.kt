package com.github.daniilbug.lifeisbetter.adapter

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.daniilbug.lifeisbetter.R
import com.github.daniilbug.lifeisbetter.viewmodel.MailView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView

class MailListAdapter(
    private val onClick: (card: View, messageId: String) -> Unit
): ListAdapter<MailView, MailListAdapter.ViewHolder>(
    DiffCallback
) {
    object DiffCallback: DiffUtil.ItemCallback<MailView>() {
        override fun areContentsTheSame(oldItem: MailView, newItem: MailView): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: MailView, newItem: MailView): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_message, parent, false)
        return ViewHolder(view).apply {
            view.setOnClickListener { onItemClicked(adapterPosition) }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val messageText: MaterialTextView = itemView.findViewById(R.id.itemMessageText)
        private val messageImage: AppCompatImageView = itemView.findViewById(R.id.itemMessageImage)
        private val messageDate: MaterialTextView = itemView.findViewById(R.id.itemMessageDate)
        private val messageCard: MaterialCardView = itemView.findViewById(R.id.messageItemCard)

        fun bind(position: Int) {
            val message = getItem(position)
            itemView.transitionName = message.id
            messageText.text = message.text
            messageDate.text = DateFormat.getDateFormat(itemView.context).format(message.date)
            val imageResource = if (message.isOpened) R.drawable.ic_letter_opened else R.drawable.ic_letter_closed
            messageImage.setImageResource(imageResource)
        }

        fun onItemClicked(position: Int) {
            val message = getItem(position)
            onClick(messageCard, message.id)
        }
    }
}