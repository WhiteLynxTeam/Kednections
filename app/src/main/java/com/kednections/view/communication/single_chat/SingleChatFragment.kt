package com.kednections.view.communication.single_chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.kednections.R
import com.kednections.core.base.BaseFragment
import com.kednections.databinding.FragmentSingleChatBinding
import com.kednections.domain.models.chats.SingleChat
import com.kednections.view.activity.MainActivity

class SingleChatFragment : BaseFragment<FragmentSingleChatBinding>() {

    val singleChat = listOf(
        SingleChat(
            "новый мэтч!!!\n\nна три вещи можно смотреть вечно: на огонь, воду, и шоты в твоих \u2028дизайн-витринах.\n\nнапиши сообщение и проверь, как далеко заведёт этот мэтч.",
            "10:47",
            isMatch = true
            ),
        SingleChat(
            "Привет! Классные работы!\nКак\nтвои\nдела?\nРасскажи\nмне",
            "10:49",
            isSent = true
        ),
        SingleChat(
            "Привет! Все хорошо, ты как",
            "10:50"
        ),
        SingleChat(
            "?",
            "10:51"
        ),
        SingleChat(
            "Привет! Классные работы!\nКак\nтвои\nдела?\nРасскажи\nмне",
            "10:53",
            isSent = true
        ),
        SingleChat(
            "Привет! Все хорошо, ты как",
            "10:54"
        ),
        SingleChat(
            "?",
            "10:58"
        )
    )

    private lateinit var adapter: SingleChatAdapter

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSingleChatBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SingleChatAdapter()
        binding.rcChat.layoutManager = LinearLayoutManager(requireContext())
        binding.rcChat.adapter = adapter
        adapter.setItems(singleChat)


        setupMessageInput()

        binding.messageEditText.addTextChangedListener {
            val text = it.toString()
            if (text.isNotBlank()) {
                binding.messageInputLayout.endIconDrawable =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_send_btn_orange)
            } else {
                binding.messageInputLayout.endIconDrawable =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_add_document)
            }
        }

        (activity as MainActivity).setUIVisibility(showBottom = false)
    }

    private fun setupMessageInput() {
        binding.messageEditText.addTextChangedListener {
            val text = it.toString()
            if (text.isNotBlank()) {
                // Показываем оранжевую иконку отправки
                binding.messageInputLayout.endIconDrawable =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_send_btn_orange)

                // Устанавливаем обработчик только для иконки отправки
                binding.messageInputLayout.setEndIconOnClickListener {
                    sendMessage(text)
                }
            } else {
                // Показываем стандартную иконку добавления
                binding.messageInputLayout.endIconDrawable =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_add_document)

                // Убираем обработчик отправки когда текст пустой
                binding.messageInputLayout.setEndIconOnClickListener(null)
            }
        }

        // Убираем обработчик Enter - сообщения отправляются только по клику на иконку
        binding.messageEditText.setOnEditorActionListener { _, _, _ ->
            false // Не обрабатываем Enter
        }
    }

    private fun sendMessage(text: String) {
        val newMessage = SingleChat(
            message = text,
            date = getCurrentTime(),
            isSent = true,
            isMatch = false
        )

        adapter.addItem(newMessage)
        binding.messageEditText.text?.clear()
        binding.rcChat.scrollToPosition(adapter.itemCount - 1)

    }

    private fun getCurrentTime(): String {
        return java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
            .format(java.util.Date())
    }

}