package com.kednections.view.form.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.kednections.R
import com.kednections.core.base.BaseFragment
import com.kednections.databinding.FragmentIntroBinding
import com.kednections.view.activity.FormActivity

class IntroFragment : BaseFragment<FragmentIntroBinding>() {

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentIntroBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Скрываем заголовок активности (если нужно)
        (activity as FormActivity).setUIVisibility(showHeader = false)

        val player = ExoPlayer.Builder(requireContext()).build()
        binding.video.player = player

        val mediaItem = MediaItem.fromUri(
            "android.resource://${requireContext().packageName}/${R.raw.video_intro}".toUri()
        )

        player.setMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady = true

        player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) {
                    if (isAdded) findNavController().navigate(R.id.welcomeFragment)
                }
            }
        })

    }
}