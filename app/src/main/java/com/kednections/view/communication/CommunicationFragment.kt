package com.kednections.view.communication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kednections.R
import com.kednections.core.base.BaseFragment
import com.kednections.databinding.FragmentCommunicationBinding
import com.kednections.domain.models.chats.Chat
import com.kednections.view.activity.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CommunicationFragment : BaseFragment<FragmentCommunicationBinding>() {

    val chats = listOf(
        Chat(
            R.drawable.img_ava_1_selected,
            "Креативный Кот",
            "новый мэтч!!!",
            "22:40",
            1,
            true
        ),
        Chat(
            R.drawable.img_ava_4_selected,
            "Креативная Фрида",
            "новый мэтч!!!",
            "21:30",
            2
        ),
        Chat(
            R.drawable.img_ava_2_selected,
            "Андрей Петров",
            "новый мэтч!!!",
            "10:40",
            1
        ),
        Chat(
            R.drawable.img_ava_10_selected,
            "Бешеный конь",
            "новый мэтч!!!",
            "15:07"
        ),
        Chat(
            R.drawable.img_ava_8_selected,
            "Креативный Кот",
            "новый мэтч!!!",
            "22:40"
        )
    )

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCommunicationBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rcChats.layoutManager = LinearLayoutManager(requireContext())
        binding.rcChats.adapter = ChatsAdapter(
            chat = chats,
            onChatClick = {
                findNavController().navigate(R.id.action_communicationFragment_to_singleChatFragment)
            })

        lifecycleScope.launch {
            delay(1500)
            binding.bgEmptyChat.visibility = View.GONE
            binding.rcChats.visibility = View.VISIBLE
        }

//        binding.btnEmpty.setOnClickListener {
//            binding.bgEmptyChat.visibility = View.VISIBLE
//            binding.rcChats.visibility = View.GONE
//        }
//
//        binding.btnChats.setOnClickListener {
//            binding.bgEmptyChat.visibility = View.GONE
//            binding.rcChats.visibility = View.VISIBLE
//        }

        (activity as MainActivity).setUIVisibility(showBottom = true)
    }
}