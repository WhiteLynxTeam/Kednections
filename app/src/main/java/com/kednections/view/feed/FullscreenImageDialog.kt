package com.kednections.view.feed

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.kednections.R
import com.kednections.databinding.DialogFullscreenImageBinding

class FullscreenImageDialog : DialogFragment() {

    companion object {
        fun newInstance(imageList: List<ImageDetail>, startPosition: Int): FullscreenImageDialog {
            return FullscreenImageDialog().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList("image_list", ArrayList(imageList))
                    putInt("start_position", startPosition)
                }
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DialogFullscreenImageBinding.inflate(inflater, container, false)
        val imageList = requireArguments().getParcelableArrayList<ImageDetail>("image_list") ?: emptyList()
        val startPosition = requireArguments().getInt("start_position", 0)

        val adapter = FullscreenPagerAdapter(imageList) { dismiss() }
        binding.fullscreenPager.adapter = adapter
        binding.fullscreenPager.setCurrentItem(startPosition, false)
        binding.pageIndicator.isVisible = imageList.size > 1
        binding.pageIndicator.attachTo(binding.fullscreenPager)


        binding.btnSkip.setImageResource(R.drawable.ic_button_skip_for_feed)
        binding.btnLike.setImageResource(R.drawable.ic_button_like_for_feed)

        val feedPosition = requireArguments().getInt("feedPosition")
        binding.btnLike.setOnClickListener {
            binding.btnLike.setImageResource(R.drawable.ic_button_like_for_feed_pressed)
            setFragmentResult("fullscreen_result", Bundle().apply {
                putString("action", "like")
                putInt("feedPosition", feedPosition)
            })
        }

        binding.btnSkip.setOnClickListener {
            binding.btnSkip.setImageResource(R.drawable.ic_button_skip_for_feed_pressed)
            setFragmentResult("fullscreen_result", Bundle().apply {
                putString("action", "skip")
                putInt("feedPosition", feedPosition)
            })
        }


        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setBackgroundDrawable(Color.BLACK.toDrawable())
    }
}