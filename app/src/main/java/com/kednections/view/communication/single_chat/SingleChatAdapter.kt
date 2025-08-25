package com.kednections.view.communication.single_chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kednections.databinding.ItemSingleChatBinding
import com.kednections.domain.models.chats.SingleChat

class SingleChatAdapter : RecyclerView.Adapter<SingleChatAdapter.SingleChatViewHolder>() {

    private val singleChatList = mutableListOf<SingleChat>()

    inner class SingleChatViewHolder(private val binding: ItemSingleChatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(singleChat: SingleChat) {
            with(binding) {

                matchMessage.visibility = View.GONE
                sentMessage.visibility = View.GONE
                receivedMessage.visibility = View.GONE

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

    // Функция для добавления одного элемента
    fun addItem(singleChat: SingleChat) {
        singleChatList.add(singleChat)
        notifyItemInserted(singleChatList.size - 1)
    }

//    // Функция для добавления списка элементов
//    fun addItems(items: List<SingleChat>) {
//        val startPosition = singleChatList.size
//        singleChatList.addAll(items)
//        notifyItemRangeInserted(startPosition, items.size)
//    }
//
//    // Функция для очистки всех элементов
//    fun clearItems() {
//        singleChatList.clear()
//        notifyDataSetChanged()
//    }

    // Функция для установки нового списка
    fun setItems(items: List<SingleChat>) {
        singleChatList.clear()
        singleChatList.addAll(items)
        notifyDataSetChanged()
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
        holder.bind(singleChatList[position])
    }

    override fun getItemCount(): Int = singleChatList.size
}