package com.kednections.view.profile.showcase

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.kednections.core.base.BaseFragment
import com.kednections.databinding.FragmentShowCaseBinding
import com.kednections.utils.startMarquee
import com.kednections.view.activity.MainActivity
import com.kednections.view.activity.MainActivityViewModel
import com.kednections.view.profile.showcase.AddImagesAdapter.Companion.MAX_ITEMS
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
            // Для множественного выбора
            if (intent.clipData != null) {
                val clipData = intent.clipData!!
                for (i in 0 until clipData.itemCount) {
                    if (imageUris.size >= MAX_ITEMS) break
                    clipData.getItemAt(i).uri?.let { uri ->
                        imageUris.add(uri)
                    }
                }
            }
            // Для единичного выбора
            else {
                intent.data?.let { uri ->
                    if (imageUris.size < MAX_ITEMS) {
                        imageUris.add(uri)
                    }
                }
            }

            adapter.notifyDataSetChanged()
            binding.btnPublish.isEnabled = imageUris.isNotEmpty()
        }
    }

//    private val galleryLauncher = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) { result ->
//        result.data?.data?.let { uri ->
//            if (imageUris.size < MAX_ITEMS) {
//                imageUris.add(uri)
//                adapter.notifyItemInserted(imageUris.size - 1)
//                // Обновляем кнопку добавления
//                adapter.notifyItemChanged(imageUris.size)
//                binding.btnPublish.isEnabled = imageUris.isNotEmpty()
//            }
//        }
//    }

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentShowCaseBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Бегущая строка (Анимация)
        startMarquee(binding.textDescription, binding.textHorizontalScroll, speed = 4500L)
        startMarquee(binding.textDescription2, binding.textHorizontalScroll2, speed = 5500L)

        (activity as MainActivity).setUIVisibility(showHeader = false)

        // Рассчитываем размеры элементов
        val displayMetrics = resources.displayMetrics
        itemWidth = (displayMetrics.widthPixels * 0.278).toInt()
        itemHeight = (displayMetrics.heightPixels * 0.137).toInt()

        setupRecyclerView()
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
//            activityViewModel.selectedImages.value = imageUris.toList()
//            profileViewModel.isProfileTop.value = false
            activityViewModel.saveImages(imageUris)
            activityViewModel.setIsProfileTop(false)

            // Возвращаемся назад
            findNavController().popBackStack()
        }


    }

    private fun setupRecyclerView() {
        adapter = AddImagesAdapter(
            items = imageUris,
            itemWidth = itemWidth,
            itemHeight = itemHeight,
            onAddClick = { openGallery() }
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

    private fun openGallery() {
        if (imageUris.size >= MAX_ITEMS) return

        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true) // Ключевое изменение
        }
        galleryLauncher.launch(intent)
    }

//    private fun openGallery() {
//        if (imageUris.size >= 6) return
//
//        val intent =
//            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
//                type = "image/*"
//            }
//        galleryLauncher.launch(intent)
//    }

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

