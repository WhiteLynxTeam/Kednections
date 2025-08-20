package com.kednections.view.feed.screen_saver

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.OvershootInterpolator
import android.widget.ImageButton
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.kednections.R
import com.kednections.core.base.BaseFragment
import com.kednections.databinding.FragmentScreenSaverBinding
import com.kednections.view.activity.MainActivity
import dagger.android.support.AndroidSupportInjection

class ScreenSaverFragment : BaseFragment<FragmentScreenSaverBinding>() {

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // Логируем нажатие
            android.util.Log.d("ScreenSaverFragment", "System back button pressed")

            // Дополнительные действия при нажатии назад
            findNavController().popBackStack()
        }
    }

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentScreenSaverBinding.inflate(inflater, container, false)

    private fun dpToPx(dp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            resources.displayMetrics
        )
    }

    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val player = ExoPlayer.Builder(requireContext()).build()
        binding.video.player = player

        val mediaItem = MediaItem.fromUri(
            "android.resource://${requireContext().packageName}/${R.raw.clear}".toUri()
        )

        player.setMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady = true


        // Слушатель изменения размера клавиатуры
        binding.root.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            binding.root.getWindowVisibleDisplayFrame(rect)
            val screenHeight = binding.root.height
            val keyboardHeight = screenHeight - rect.bottom

            if (keyboardHeight > screenHeight * 0.15) { // Клавиатура открыта
                // Поднимаем Guideline
                binding.guidelineEtInput.setGuidelinePercent(0.579f)
            } else {
                // Возвращаем Guideline на исходную позицию
                binding.guidelineEtInput.setGuidelinePercent(0.802f)
            }
        }

        binding.root.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)

                val root = binding.root
                val leftImage = binding.imgLeft
                val rightImage = binding.imgRight
                val writeMessage = binding.writeMessage
                val profileText = binding.tvViewProfile
                val textMatch = binding.tvMatch
                val textTrue = binding.tvTrue

                val rootWidth = root.width.toFloat()

                val leftStartX = leftImage.x
                val rightStartX = rightImage.x
                val rightStartRight = rightStartX + rightImage.width
                val writeStartY = writeMessage.y
                textMatch.translationY = -textMatch.height.toFloat() - dpToPx(50f)
                textTrue.translationY = -textTrue.height.toFloat() - dpToPx(80f)
                textMatch.alpha = 0f
                textTrue.alpha = 0f

                leftImage.alpha = 0.8f
                rightImage.alpha = 0.8f
                writeMessage.alpha = 0.3f
                profileText.alpha = 0f

                leftImage.translationX = -leftImage.width.toFloat() - dpToPx(30f)
                rightImage.translationX = rootWidth + dpToPx(30f)
                writeMessage.translationY = root.height.toFloat() - writeStartY + dpToPx(30f)

                val writeLeft = writeMessage.x
                val writeRight = writeLeft + writeMessage.width

                val leftTargetTx = writeLeft - leftStartX
                val rightTargetTx = writeRight - rightStartRight

                val titleAnim =
                    ObjectAnimator.ofFloat(textMatch, "translationY", textMatch.translationY, 0f)
                        .apply {
                            interpolator = OvershootInterpolator(1.2f)
                            duration = 400L
                        }
                val titleFade = ObjectAnimator.ofFloat(textMatch, "alpha", 0f, 1f)

                val subtitleAnim =
                    ObjectAnimator.ofFloat(textTrue, "translationY", textTrue.translationY, 0f)
                        .apply {
                            interpolator = OvershootInterpolator(1.2f)
                            duration = 400L
                        }

                val subtitleFade = ObjectAnimator.ofFloat(textTrue, "alpha", 0f, 1f)

                // Группируем анимации текста с задержками
                val topTextsAnim = AnimatorSet().apply {
                    playTogether(titleAnim, titleFade)
                    startDelay = 100L
                }

                val topSubtitleAnim = AnimatorSet().apply {
                    playTogether(subtitleAnim, subtitleFade)
                    startDelay = 250L
                }

                val fullTopTextsAnim = AnimatorSet().apply {
                    playSequentially(topTextsAnim, topSubtitleAnim)
                }

                // 2. Анимации картинок (встречаются)
                val moveLeft = ObjectAnimator.ofFloat(
                    leftImage,
                    "translationX",
                    leftImage.translationX,
                    leftTargetTx
                ).apply {
                    interpolator = OvershootInterpolator(1.1f)
                }

                val moveRight = ObjectAnimator.ofFloat(
                    rightImage,
                    "translationX",
                    rightImage.translationX,
                    rightTargetTx
                ).apply {
                    interpolator = OvershootInterpolator(1.1f)
                }

                val fadeLeft = ObjectAnimator.ofFloat(leftImage, "alpha", 0.8f, 1f)
                val fadeRight = ObjectAnimator.ofFloat(rightImage, "alpha", 0.8f, 1f)

                // 3. Анимация сообщения (появление снизу)
                val moveWriteUp = ObjectAnimator.ofFloat(
                    writeMessage,
                    "translationY",
                    100f,
                    0f
                )

                val fadeWrite = ObjectAnimator.ofFloat(writeMessage, "alpha", 0.3f, 1f).apply {
                    duration = 1500L
                }

                // 4. Анимация текста профиля (появление)
                val fadeProfile = ObjectAnimator.ofFloat(profileText, "alpha", 0f, 1f).apply {
                    duration = 2500L
                }

                // 5. Анимации картинок (разъезжаются вверх/вниз)
                val leftDown = ObjectAnimator.ofFloat(leftImage, "translationY", 1f, dpToPx(100f))
                val rightUp = ObjectAnimator.ofFloat(rightImage, "translationY", 1f, -dpToPx(150f))


                val meetAnim = AnimatorSet().apply {
                    playTogether(moveLeft, moveRight, fadeLeft, fadeRight)
                    duration = 800L
                }

                val separateAnim = AnimatorSet().apply {
                    playTogether(leftDown, rightUp)
                    duration = 700L
                }

                val fullImageAnim = AnimatorSet().apply {
                    playSequentially(meetAnim, separateAnim) // встретились -> разъехались
                }

                val otherAnimations = AnimatorSet().apply {
                    playTogether(
                        fullTopTextsAnim,
                        moveWriteUp, fadeWrite,
                        fadeProfile
                    )
                }

                val fullAnimation = AnimatorSet().apply {
                    playTogether(fullImageAnim, otherAnimations)
                }

                player.addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(state: Int) {
                        if (state == Player.STATE_ENDED) {
                            binding.icClosed.visibility = View.VISIBLE
                            fullAnimation.start()
                        }
                    }
                })

            }
        })

        val til = binding.writeMessage
        val et = binding.etWriteMessage

        val endIcon = til.findViewById<ImageButton>(
            com.google.android.material.R.id.text_input_end_icon
        )

        et.doAfterTextChanged { text ->
            val colorRes =
                if (!text.isNullOrBlank()) R.color.orange else R.color.bg_text_description
            val color = ContextCompat.getColor(requireActivity(), colorRes)
            endIcon.imageTintList = ColorStateList.valueOf(color)
        }

        (activity as MainActivity).setUIVisibility(
            showBottom = false
        )

    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

}