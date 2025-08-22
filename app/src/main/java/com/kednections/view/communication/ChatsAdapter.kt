package com.kednections.view.communication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kednections.databinding.ItemChatBinding
import com.kednections.domain.models.chats.Chat

class ChatsAdapter(
    private val chat: List<Chat>,
    private val onChatClick: (Chat) -> Unit
) : RecyclerView.Adapter<ChatsAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(private val binding: ItemChatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(chat: Chat) {
            with(binding) {
                avatar.setImageResource(chat.avatar)
                name.text = chat.name
                message.text = chat.message
                time.text = chat.time
                count.text = chat.count.toString()

                if (chat.count == 0) count.visibility = View.GONE
                if (chat.matchIndicator) matchIndicator.visibility = View.VISIBLE
                root.setOnClickListener {
                    onChatClick(chat)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemChatBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ChatViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(chat[position])
    }

    override fun getItemCount(): Int = chat.size
}