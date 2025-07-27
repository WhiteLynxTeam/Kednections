package com.kednections.view.form.avatar

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kednections.R
import com.kednections.databinding.FragmentAvatarBinding
import com.kednections.utils.startMarquee
import com.kednections.view.activity.FormActivityViewModel
import dagger.android.support.AndroidSupportInjection


class AvatarFragment : Fragment() {

    private var _binding: FragmentAvatarBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FormActivityViewModel by activityViewModels()
    private var selectedImageView: ImageView? = null

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAvatarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageMap: Map<ImageView, Pair<Int, Int>> = mapOf(
            binding.ava1 to (R.drawable.img_ava_1 to R.drawable.img_ava_1_selected),
            binding.ava2 to (R.drawable.img_ava_2 to R.drawable.img_ava_2_selected),
            binding.ava3 to (R.drawable.img_ava_3 to R.drawable.img_ava_3_selected),
            binding.ava4 to (R.drawable.img_ava_4 to R.drawable.img_ava_4_selected),
            binding.ava5 to (R.drawable.img_ava_5 to R.drawable.img_ava_5_selected),
            binding.ava6 to (R.drawable.img_ava_6 to R.drawable.img_ava_6_selected),
            binding.ava7 to (R.drawable.img_ava_7 to R.drawable.img_ava_7_selected),
            binding.ava8 to (R.drawable.img_ava_8 to R.drawable.img_ava_8_selected)
        )

        //бегущая строка (Анимация)
        startMarquee(binding.textDescription, binding.textHorizontalScroll, speed = 5000L)

        // Назначаем каждому ImageView обработчик клика
        imageMap.forEach { (imageView, imagePair) ->
            imageView.setOnClickListener {
                // Возврат предыдущего в исходное состояние
                selectedImageView?.let { prev ->
                    val prevImages = imageMap[prev]
                    prevImages?.let { (original, _) ->
                        prev.setImageResource(original)
                    }
                }

                // Установка нового изображения
                imageView.setImageResource(imagePair.second)

                // Обновляем текущий активный ImageView
                selectedImageView = imageView

                // Активировать кнопку
                binding.btnResume.isEnabled = true
            }
        }

        binding.add.setOnClickListener {
            animImageAdd(it)
        }

        binding.btnResume.setOnClickListener {
            findNavController().navigate(R.id.action_avatarFragment_to_specializationFragment)
            viewModel.increaseProgress()
        }
    }
}

private fun animImageAdd(view: View) {
    val animator = ObjectAnimator.ofFloat(view, "rotation", 0f, 90f)
    animator.duration = 500
    animator.start()
}