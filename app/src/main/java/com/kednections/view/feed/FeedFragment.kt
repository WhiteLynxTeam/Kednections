package com.kednections.view.feed

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.kednections.R
import com.kednections.databinding.FragmentFeedBinding
import com.kednections.utils.startMarquee
import dagger.android.support.AndroidSupportInjection
import androidx.core.view.isVisible
import com.kednections.view.feed.filter.NothingFilterDialog

class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: FeedAdapter

    val feedList = mutableListOf(
        Feed(
            images = listOf(
                ImageDetail(
                    imageRes = R.drawable.image,
                    title = "Моя работа",
                    description = "Последний проект для клиента",
                    likes = 42
                ),
                ImageDetail(
                    imageRes = R.drawable.image,
                    title = "Процесс",
                    description = "Как я это делал",
                    likes = 28
                ),
                ImageDetail(
                    imageRes = R.drawable.image,
                    title = "Процесс",
                    description = "Как я это делал",
                    likes = 28
                ),
                ImageDetail(
                    imageRes = R.drawable.image,
                    title = "Процесс",
                    description = "Как я это делал",
                    likes = 28
                )
            ),
            city = "Москва",
            avatar = R.drawable.img_ava_1,
            name = "Дизайнер",
            specialization = "UX/UI дизайнер, Веб-дизайнер, продуктовый дизайнер",
            isOnline = true,
            isSubscribe = false
        ),
        Feed(
            images = listOf(
                ImageDetail(
                    imageRes = R.drawable.image,
                    title = "Моя работа",
                    description = "Последний проект для клиента",
                    likes = 42
                ),
                ImageDetail(
                    imageRes = R.drawable.image,
                    title = "Процесс",
                    description = "Как я это делал",
                    likes = 28
                ),
                ImageDetail(
                    imageRes = R.drawable.image,
                    title = "Процесс",
                    description = "Как я это делал",
                    likes = 28
                )
            ),
            "Москва",
            R.drawable.img_ava_2_selected,
            "Креативная фрида 2",
            "UX/UI-дизайнер, продуктовый дизайнер"
        ),
        Feed(
            images = listOf(
                ImageDetail(
                    imageRes = R.drawable.image,
                    title = "Моя работа",
                    description = "Последний проект для клиента",
                    likes = 42
                )
            ),
            "Новосибирск",
            R.drawable.img_ava_3,
            "Креативная фрида 3",
            "UX/UI-дизайнер, Веб-дизайнер",
            isOnline = true
        ),
        Feed(
            images = listOf(
                ImageDetail(
                    imageRes = R.drawable.image,
                    title = "Моя работа",
                    description = "Последний проект для клиента",
                    likes = 42
                ),
                ImageDetail(
                    imageRes = R.drawable.image,
                    title = "Процесс",
                    description = "Как я это делал",
                    likes = 28
                )
            ),
            "Сочи",
            R.drawable.img_ava_4_selected,
            "Креативная фрида 4",
            "Веб-дизайнер, продуктовый дизайнер",
            isSubscribe = true
        )
    )

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = binding.viewPager

        adapter = FeedAdapter(
            feedList,
            onLike = { position -> removeFeed(position) },
            onSkip = { position -> removeFeed(position) },
            onImageClick = { imageDetail ->
                FullscreenImageDialog.newInstance(imageDetail)
                    .show(parentFragmentManager, "dialog")
            },
            fragment = this
        )

        binding.viewPager.adapter = adapter
        binding.viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL

//        setupProgressBar(feedList.size)

//        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                updateProgressBar(position)
//            }
//        })

        binding.icFilter.setOnClickListener {
            if (binding.bgTheEnd.isVisible) {
                NothingFilterDialog().show(parentFragmentManager, "NothingFilterDialog")
            } //else findNavController().navigate(R.id.action_feedFragment_to_filterFeedFragment)
        }

    }

//    private fun setupProgressBar(total: Int) {
//        val container = binding.progressContainer
//        container.removeAllViews()
//
//        repeat(total) {
//            val segment = View(requireActivity()).apply {
//                layoutParams =
//                    LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f).apply {
//                        marginEnd = if (it < total - 1) 8 else 0
//                    }
//                setBackgroundColor(Color.LTGRAY)
//            }
//            container.addView(segment)
//        }
//    }

//    private fun updateProgressBar(currentIndex: Int) {
//        val container = binding.progressContainer
//        for (i in 0 until container.childCount) {
//            val segment = container.getChildAt(i)
//            segment.setBackgroundColor(
//                if (i <= currentIndex) Color.WHITE else Color.argb(128, 255, 255, 255)
//            )
//        }
//    }

    private fun removeFeed(positionToRemove: Int) {
        if (feedList.isEmpty() || positionToRemove !in feedList.indices) return

        val wasLastItem = positionToRemove == feedList.lastIndex
        adapter.removeAt(positionToRemove)

        if (wasLastItem || feedList.isEmpty()) {
            binding.viewPager.visibility = View.GONE
            binding.bgTheEnd.visibility = View.VISIBLE
            binding.textHorizontalScroll.visibility = View.VISIBLE
            startMarquee(binding.textDescription, binding.textHorizontalScroll, speed = 5000L)
        } else {
            binding.viewPager.setCurrentItem(positionToRemove.coerceAtMost(feedList.size - 1), false)
        }
    }

//    private fun removeFeed(positionToRemove: Int) {
//        if (feedList.isEmpty()) return
//        if (positionToRemove !in feedList.indices) return
//
//        // Проверяем, был ли это последний элемент ДО удаления
//        val wasLastItem = positionToRemove == feedList.lastIndex
//
//        // Удаляем элемент
//        adapter.removeAt(positionToRemove)
//
//        // Удаляем соответствующий сегмент прогресс-бара
//        if (positionToRemove < binding.progressContainer.childCount) {
//            binding.progressContainer.removeViewAt(positionToRemove)
//        }
//
//        // Если это был последний элемент ИЛИ теперь список пуст
//        if (wasLastItem || feedList.isEmpty()) {
//            binding.viewPager.visibility = View.GONE
//            binding.progressContainer.visibility = View.GONE
//            binding.bgTheEnd.visibility = View.VISIBLE
//            binding.textHorizontalScroll.visibility = View.VISIBLE
//            startMarquee(binding.textDescription, binding.textHorizontalScroll, speed = 5000L)
//            return
//        }
//
//        // Обновляем позицию
////        val newPosition = positionToRemove
//
////        binding.viewPager.setCurrentItem(newPosition, false)
////        updateProgressBar(newPosition)
//    }
}