package com.kednections.view.form.success_reg

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.kednections.R
import com.kednections.core.base.BaseFragment
import com.kednections.databinding.FragmentSuccessRegBinding
import com.kednections.view.activity.FormActivity
import com.kednections.view.activity.MainActivity

class SuccessRegFragment : BaseFragment<FragmentSuccessRegBinding>() {

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSuccessRegBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val player = ExoPlayer.Builder(requireContext()).build()
        binding.video.player = player

        val mediaItem = MediaItem.fromUri(
            "android.resource://${requireContext().packageName}/${R.raw.success_reg_video}".toUri()
        )

        player.setMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady = true

        player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) {
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                }
            }
        })

        (activity as FormActivity).setUIVisibility(
            showHeader = false
        )

    }

}