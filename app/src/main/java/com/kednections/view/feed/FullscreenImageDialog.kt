package com.kednections.view.feed

import android.app.Dialog
import android.content.DialogInterface
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
import com.kednections.domain.models.feed.ImageDetail

class FullscreenImageDialog : DialogFragment() {

    private var selectedAction: String? = null
    private var feedPosition: Int = 0

    companion object {
        fun newInstance(
            imageList: List<ImageDetail>,
            startPosition: Int,
            feedPosition: Int
        ): FullscreenImageDialog {
            return FullscreenImageDialog().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList("image_list", ArrayList(imageList))
                    putInt("start_position", startPosition)
                    putInt("feedPosition", feedPosition)
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

        val adapter = FullscreenPagerAdapter(imageList)
        binding.fullscreenPager.adapter = adapter
        binding.fullscreenPager.setCurrentItem(startPosition, false)
        binding.pageIndicator.isVisible = imageList.size > 1
        binding.pageIndicator.attachTo(binding.fullscreenPager)

        binding.btnSkip.setImageResource(R.drawable.ic_button_skip_for_feed)
        binding.btnLike.setImageResource(R.drawable.ic_button_like_for_feed)

        feedPosition = requireArguments().getInt("feedPosition")

        binding.btnClosed.setOnClickListener {
            dismiss() // Закрываем диалог
        }

        binding.btnLike.setOnClickListener {
            if (selectedAction == "like") {
                // Сброс выбора
                binding.btnLike.setImageResource(R.drawable.ic_button_like_for_feed)
                selectedAction = null
            } else {
                // Новый выбор
                binding.btnLike.setImageResource(R.drawable.ic_button_like_for_feed_pressed)
                binding.btnSkip.setImageResource(R.drawable.ic_button_skip_for_feed)
                selectedAction = "like"
            }
        }

        binding.btnSkip.setOnClickListener {
            if (selectedAction == "skip") {
                // Сброс выбора
                binding.btnSkip.setImageResource(R.drawable.ic_button_skip_for_feed)
                selectedAction = null
            } else {
                // Новый выбор
                binding.btnSkip.setImageResource(R.drawable.ic_button_skip_for_feed_pressed)
                binding.btnLike.setImageResource(R.drawable.ic_button_like_for_feed)
                selectedAction = "skip"
            }
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

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        // Отправляем результат только при закрытии диалога
        selectedAction?.let { action ->
            setFragmentResult("fullscreen_result", Bundle().apply {
                putString("action", action)
                putInt("feedPosition", feedPosition)
            })
        }
    }
}