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
import com.kednections.domain.models.Ava
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

        val imageMap: Map<ImageView, Ava> = mapOf(
            binding.ava1 to Ava.AVA1,
            binding.ava2 to Ava.AVA2,
            binding.ava3 to Ava.AVA3,
            binding.ava4 to Ava.AVA4,
            binding.ava5 to Ava.AVA5,
            binding.ava6 to Ava.AVA6,
            binding.ava7 to Ava.AVA7,
            binding.ava8 to Ava.AVA8,
        )

        //бегущая строка (Анимация)
        startMarquee(binding.textDescription, binding.textHorizontalScroll, speed = 5000L)

        // Назначаем каждому ImageView обработчик клика
        imageMap.forEach { (imageView, ava) ->
            imageView.setOnClickListener {
                // Возврат предыдущего в исходное состояние
                selectedImageView?.let { prev ->
                    val prevAvaImages = imageMap[prev]
                    if (prevAvaImages != null) {
                        prev.setImageResource(prevAvaImages.imgRes)
                    }
                }

                // Установка нового изображения
                imageView.setImageResource(ava.imgResSelected)

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
                    photo = currentUserAvatar,
                    status = selectedImageView?.let { imgView ->
                        imageMap[imgView]?.name
                    }
                )
            }
            findNavController().navigate(R.id.action_avatarFragment_to_specializationFragment)
            activityViewModel.increaseProgress()
        }

        binding.skipped.setOnClickListener {
            activityViewModel.updateData {
                it.copy(
                    status = Ava.AVA0.name,
                    photo = null,
                )
            }
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

