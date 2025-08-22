package com.kednections.view.communication.single_chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kednections.databinding.ItemSingleChatBinding
import com.kednections.domain.models.chats.SingleChat

class SingleChatAdapter(
    private val singleChat: List<SingleChat>
) : RecyclerView.Adapter<SingleChatAdapter.SingleChatViewHolder>() {

    inner class SingleChatViewHolder(private val binding: ItemSingleChatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(singleChat: SingleChat) {
            with(binding) {

                if (singleChat.isMatch) {
                    matchMessage.visibility = View.VISIBLE
                    tvMatchMessage.text = singleChat.message
                    matchDate.text = singleChat.date
                } else if (singleChat.isSent) {
                    sentMessage.visibility = View.VISIBLE
                    tvSentMessage.text = singleChat.message
                    sentDate.text = singleChat.date
                } else {
                    receivedMessage.visibility = View.VISIBLE
                    tvReceivedMessage.text = singleChat.message
                    receivedDate.text = singleChat.date
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleChatViewHolder {
        val binding = ItemSingleChatBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SingleChatViewHolder(binding)
    }


    override fun onBindViewHolder(holder: SingleChatViewHolder, position: Int) {
        holder.bind(singleChat[position])
    }

    override fun getItemCount(): Int = singleChat.size
}