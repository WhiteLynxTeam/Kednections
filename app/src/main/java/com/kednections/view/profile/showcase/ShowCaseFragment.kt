package com.kednections.view.profile.showcase

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.kednections.R
import com.kednections.core.base.BaseFragment
import com.kednections.databinding.FragmentShowCaseBinding
import com.kednections.utils.startMarquee
import com.kednections.view.activity.MainActivity
import com.kednections.view.activity.MainActivityViewModel
import com.kednections.view.profile.editing_image.BackInEditingDialog
import com.kednections.view.profile.showcase.AddImagesAdapter.Companion.MAX_ITEMS
import kotlinx.coroutines.launch
import java.util.Collections

class ShowCaseFragment : BaseFragment<FragmentShowCaseBinding>() {

    private val activityViewModel: MainActivityViewModel by activityViewModels()
    private lateinit var adapter: AddImagesAdapter
    private val imageUris = mutableListOf<Uri>()
    private var itemWidth = 0
    private var itemHeight = 0

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        result.data?.let { intent ->
            val newUris = mutableListOf<Uri>().apply {
                addAll(imageUris) // добавляем существующие URI
            }
            // Для множественного выбора
            if (intent.clipData != null) {
                val clipData = intent.clipData!!
                for (i in 0 until clipData.itemCount) {
                    if (newUris.size >= MAX_ITEMS) break
                    clipData.getItemAt(i).uri?.let { uri ->
                        newUris.add(uri)
                    }
                }
            }
            // Для единичного выбора
            else {
                intent.data?.let { uri ->
                    if (newUris.size < MAX_ITEMS) {
                        newUris.add(uri)
                    }
                }
            }

            imageUris.clear()
            imageUris.addAll(newUris)

            adapter.notifyDataSetChanged()
            binding.btnPublish.isEnabled = imageUris.isNotEmpty()
        }
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            handleBackPress()
        }
    }

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentShowCaseBinding.inflate(inflater, container, false)

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

        // Бегущая строка (Анимация)
        startMarquee(binding.textDescription, binding.textHorizontalScroll, speed = 4500L)
        startMarquee(binding.textDescription2, binding.textHorizontalScroll2, speed = 5500L)

        (activity as MainActivity).setUIVisibility(showBottom = false)

        // Рассчитываем размеры элементов
        val displayMetrics = resources.displayMetrics
        itemWidth = (displayMetrics.widthPixels * 0.278).toInt()
        itemHeight = (displayMetrics.heightPixels * 0.137).toInt()

        viewLifecycleOwner.lifecycleScope.launch {
            activityViewModel.selectedImages.collect { uris ->
                uris.let {
                    imageUris.clear()
                    imageUris.addAll(it)
                    setupRecyclerView()
                }

            }
        }

        binding.btnPublish.isEnabled = imageUris.isNotEmpty()

        val callback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN or
                    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
            0
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPos = viewHolder.adapterPosition
                val toPos = target.adapterPosition

                if (fromPos == adapter.itemCount - 1 || toPos == adapter.itemCount - 1) {
                    return false
                }

                Collections.swap(imageUris, fromPos, toPos)
                adapter.notifyItemMoved(fromPos, toPos)

                adapter.notifyItemChanged(fromPos)
                adapter.notifyItemChanged(toPos)
                adapter.notifyItemChanged(0)

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

            override fun isLongPressDragEnabled(): Boolean {
                return true
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.rcImage)

        binding.btnPublish.setOnClickListener {
            // Сохраняем выбранные изображения в ViewModel
            activityViewModel.saveImages(imageUris)
            activityViewModel.saveChanges() // Сохраняем как оригинальные
            activityViewModel.setIsProfileTop(false)

            // Переходим к профилю
            findNavController().navigate(R.id.action_showCaseFragment_to_profileFragment)
        }

        binding.icBack.setOnClickListener {
            handleBackPress()
        }

    }

    private fun setupRecyclerView() {
        adapter = AddImagesAdapter(
            items = imageUris,
            itemWidth = itemWidth,
            itemHeight = itemHeight,
            onAddClick = { openGallery() },
            onEditClick = { uri, position ->
                activityViewModel.saveImages(imageUris, position)
                findNavController().navigate(R.id.action_showCaseFragment_to_editingImageFragment)
            }
        )

        binding.rcImage.apply {
            layoutManager = object : GridLayoutManager(requireContext(), 3) {
                override fun supportsPredictiveItemAnimations(): Boolean = false
            }
            adapter = this@ShowCaseFragment.adapter
            itemAnimator = DefaultItemAnimator().apply {
                // Отключаем анимации изменения
                supportsChangeAnimations = false
            }
            addItemDecoration(VerticalSpaceItemDecoration(8.dpToPx()))
            setItemViewCacheSize(20) // Увеличиваем кэш
        }
    }

    private fun handleBackPress() {
        // Сравниваем текущие imageUris с оригинальными из ViewModel
        val hasChanges = imageUris != activityViewModel.originalImages.value

        if (hasChanges) {
            // Показываем диалог только если есть изменения
            BackInEditingDialog(
                onSave = {
                    // Сохраняем изменения в ViewModel
                    activityViewModel.saveImages(imageUris)
                    activityViewModel.saveChanges()
                    activityViewModel.setIsProfileTop(false)
                    findNavController().navigate(R.id.action_showCaseFragment_to_profileFragment)
                },
                onDiscard = {
                    // Восстанавливаем оригинальные изображения
                    imageUris.clear()
                    imageUris.addAll(activityViewModel.originalImages.value)
                    activityViewModel.discardChanges()
                    activityViewModel.setIsProfileTop(false)
                    findNavController().navigate(R.id.action_showCaseFragment_to_profileFragment)
                }
            ).show(parentFragmentManager, "BackInEditingDialog")
        } else {
            // Нет изменений - просто выходим
            activityViewModel.setIsProfileTop(false)
            findNavController().navigate(R.id.action_showCaseFragment_to_profileFragment)
        }
    }

    private fun openGallery() {
        if (imageUris.size >= MAX_ITEMS) return

        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true) // Ключевое изменение
        }
        galleryLauncher.launch(intent)
    }

    private fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

    class VerticalSpaceItemDecoration(
        private val verticalSpaceHeight: Int
    ) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: android.graphics.Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view)
            if (position >= 3) {
                outRect.top = verticalSpaceHeight
            }
        }
    }

}

