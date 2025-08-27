package com.kednections.view.profile.editing_image

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.kednections.R
import com.kednections.core.base.BaseFragment
import com.kednections.databinding.FragmentEditingImageBinding
import com.kednections.view.activity.MainActivity
import com.kednections.view.activity.MainActivityViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class EditingImageFragment : BaseFragment<FragmentEditingImageBinding>() {

    private val activityViewModel: MainActivityViewModel by activityViewModels()
    private lateinit var adapter: EditingImageAdapter
    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                // (опционально) попытаться сохранить доступ
                try {
                    requireContext().contentResolver.takePersistableUriPermission(
                        it, Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                } catch (_: SecurityException) { /* для GetContent может не сработать — ок */ }

                val pos = activityViewModel.selectedPosition.value
                activityViewModel.replaceImageAt(pos, it)
            }
        }

    // Добавляем callback для системной кнопки назад
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            handleBackPress()
        }
    }

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentEditingImageBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Регистрируем обработчик back press
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )

        if (activityViewModel.originalImages.value.isEmpty()) {
            activityViewModel.setOriginalImages(activityViewModel.selectedImages.value)
        }

        adapter = EditingImageAdapter { uri, position ->
            Glide.with(this)
                .load(uri)
                .into(binding.imgMain)
            activityViewModel.setSelectedPosition(position)
        }

        binding.rcImage.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcImage.adapter = adapter

        binding.etComment.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val currentLength = s?.length ?: 0
                binding.tvCharCount.text = "$currentLength/80"
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                combine(
                    activityViewModel.selectedImages,
                    activityViewModel.selectedPosition
                ) { uris, pos -> uris to pos }
                    .collect { (uris, position) ->
                        adapter.setItems(uris, position)

                        binding.change.isEnabled = uris.isNotEmpty()
                        binding.delete.isEnabled = uris.isNotEmpty()

                        if (uris.isNotEmpty() && position in uris.indices) {
                            Glide.with(this@EditingImageFragment)
                                .load(uris[position])
                                .into(binding.imgMain)
                        } else {
                            binding.imgMain.setImageDrawable(null)
                        }
                    }
            }
        }

        binding.apply {

            change.setOnClickListener {
                pickImageLauncher.launch("image/*")
            }

            delete.setOnClickListener {
                val pos = activityViewModel.selectedPosition.value
                if (pos in activityViewModel.selectedImages.value.indices) {
                    DeleteDialog {
                        activityViewModel.removeImageAt(pos)
                    }.show(parentFragmentManager, "DeleteDialog")
                }
            }

            btnSave.setOnClickListener {
                // Сохраняем изменения перед переходом
                activityViewModel.saveChanges()
                findNavController().navigate(R.id.action_editingImageFragment_to_showCaseFragment)
            }

            icBack.setOnClickListener {
                handleBackPress()
            }

        }

        (activity as MainActivity).setUIVisibility(showBottom = false)
    }

    private fun handleBackPress() {
        if (activityViewModel.hasChanges()) {
            // Показываем диалог только если есть изменения
            BackInEditingDialog(
                onSave = {
                    activityViewModel.saveChanges() // Сохраняем изменения
                    findNavController().navigate(R.id.action_editingImageFragment_to_showCaseFragment)
                },
                onDiscard = {
                    activityViewModel.discardChanges() // Отменяем изменения
                    findNavController().navigate(R.id.action_editingImageFragment_to_showCaseFragment)
                }
            ).show(parentFragmentManager, "BackInEditingDialog")
        } else {
            // Нет изменений - просто выходим
            activityViewModel.setIsProfileTop(false)
            findNavController().navigate(R.id.action_editingImageFragment_to_showCaseFragment)
        }
    }
}
