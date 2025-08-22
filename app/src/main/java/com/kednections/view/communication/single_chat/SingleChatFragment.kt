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

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSingleChatBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rcChat.layoutManager = LinearLayoutManager(requireContext())
        binding.rcChat.adapter = SingleChatAdapter(
            singleChat = singleChat)

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


}