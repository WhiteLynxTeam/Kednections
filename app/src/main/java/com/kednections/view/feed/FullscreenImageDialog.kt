package com.kednections.view.feed

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.DialogFragment
import com.kednections.databinding.DialogFullscreenImageBinding

class FullscreenImageDialog : DialogFragment() {
    companion object {
        fun newInstance(imageDetail: ImageDetail): FullscreenImageDialog {
            return FullscreenImageDialog().apply {
                arguments = Bundle().apply {
                    putParcelable("image_detail", imageDetail)
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
        val imageDetail = requireArguments().getParcelable<ImageDetail>("image_detail")!!

        with(binding) {
            imageView.setImageResource(imageDetail.imageRes)
            titleText.text = imageDetail.title
            descriptionText.text = imageDetail.description
            likesCount.text = "${imageDetail.likes} likes"

            closeButton.setOnClickListener { dismiss() }
//            likeButton.setOnClickListener {
//                // Обработка лайка
//            }
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