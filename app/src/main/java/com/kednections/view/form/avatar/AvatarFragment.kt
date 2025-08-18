package com.kednections.view.form.avatar

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kednections.R
import com.kednections.core.base.BaseFragment
import com.kednections.databinding.FragmentAvatarBinding
import com.kednections.utils.encodeBitmapToString
import com.kednections.utils.startMarquee
import com.kednections.view.activity.FormActivityViewModel
import javax.inject.Inject


class AvatarFragment : BaseFragment<FragmentAvatarBinding>() {
    private val activityViewModel: FormActivityViewModel by activityViewModels()
    private lateinit var viewModel: AvatarViewModel

    private var selectedImageView: ImageView? = null
    private var currentUserAvatar: String? = null

    private val takePhoto = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { intent ->
        if (intent.resultCode == Activity.RESULT_OK) {
            try {
                // фото с галереи
                intent.data?.data?.let { uri ->
                    binding.add.setImageURI(uri)
                }

                currentUserAvatar = encodeBitmapToString(binding.add.drawable.toBitmap())
            } catch (e: Exception) {
                println("Ошибка AvatarFragment - $e")
            }
        }
    }

    @Inject
    lateinit var vmFactory: AvatarViewModel.Factory

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAvatarBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(this, vmFactory)[AvatarViewModel::class.java]

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
//            animImageAdd(it)
            pickImageFromGallery()
        }

        binding.btnResume.setOnClickListener {
            activityViewModel.updateData {
                it.copy(
                    photo = currentUserAvatar
                )
            }
            findNavController().navigate(R.id.action_avatarFragment_to_specializationFragment)
            activityViewModel.increaseProgress()
        }

        binding.skipped.setOnClickListener {
            findNavController().navigate(R.id.action_avatarFragment_to_specializationFragment)
            activityViewModel.increaseProgress()
        }
    }

    private fun animImageAdd(view: View) {
        val animator = ObjectAnimator.ofFloat(view, "rotation", 0f, 90f)
        animator.duration = 500
        animator.start()
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        takePhoto.launch(intent)
    }
}

