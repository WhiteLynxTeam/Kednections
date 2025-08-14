package com.kednections.view.profile.showcase

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kednections.databinding.FragmentShowCaseBinding
import com.kednections.utils.startMarquee
import com.kednections.view.activity.MainActivity
import com.kednections.view.profile.ProfileViewModel
import com.kednections.view.profile.showcase.AddImagesAdapter.Companion.MAX_ITEMS
import dagger.android.support.AndroidSupportInjection

class ShowCaseFragment : Fragment() {

    private var _binding: FragmentShowCaseBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by activityViewModels()
    private lateinit var adapter: AddImagesAdapter
    private val imageUris = mutableListOf<Uri>()
    private var itemWidth = 0
    private var itemHeight = 0

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        result.data?.data?.let { uri ->
            if (imageUris.size < MAX_ITEMS) {
                imageUris.add(uri)
                adapter.notifyItemInserted(imageUris.size - 1)
                // Обновляем кнопку добавления
                adapter.notifyItemChanged(imageUris.size)
                binding.btnPublish.isEnabled = imageUris.isNotEmpty()
            }
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowCaseBinding.inflate(inflater, container, false)
        return binding.root
    }

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

        binding.btnPublish.setOnClickListener {
                // Сохраняем выбранные изображения в ViewModel
                profileViewModel.selectedImages.value = imageUris.toList()

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
        if (imageUris.size >= 6) return

        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"
        }
        galleryLauncher.launch(intent)
    }

    private fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

}

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