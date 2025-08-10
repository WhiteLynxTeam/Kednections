package com.kednections.view.feed

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.kednections.R
import com.kednections.databinding.FragmentFeedBinding
import com.kednections.domain.models.feed.Feed
import com.kednections.domain.models.feed.ImageDetail
import com.kednections.utils.startMarquee
import com.kednections.view.activity.MainActivity
import com.kednections.view.feed.filter.NothingFilterDialog
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: FeedAdapter

    val feedList = mutableListOf(
        Feed(
            images = listOf(
                ImageDetail(
                    imageRes = R.drawable.image_2,
                    comment = "Очень-очень-очень-очень-очень-очень-очень-очень, максимально длинный комммммент."
                ),
                ImageDetail(
                    imageRes = R.drawable.image,
                    comment = "Комментарий 2"
                ),
                ImageDetail(
                    imageRes = R.drawable.image_2,
                    comment = "Очень-очень-очень, максимально длинный комммммент."
                ),
                ImageDetail(
                    imageRes = R.drawable.image,
                    comment = "очень длинный комммммент."
                )
            ),
            city = "Комсомольск-на-Амуре",
            avatar = R.drawable.img_ava_1_selected,
            name = "дизайнер",
            specialization = "UX/UI дизайнер, Веб-дизайнер, продуктовый дизайнер",
            isOnline = true
        ),
        Feed(
            images = listOf(
                ImageDetail(
                    imageRes = R.drawable.image,
                    comment = "Комментарий 1"
                ),
                ImageDetail(
                    imageRes = R.drawable.image_2,
                    comment = "Комментарий 2"
                ),
                ImageDetail(
                    imageRes = R.drawable.image,
                    comment = "Комментарий 3"
                )
            ),
            "Москва",
            R.drawable.img_ava_8_selected,
            "Креативная фрида 2",
            "UX/UI-дизайнер, продуктовый дизайнер"
        ),
        Feed(
            images = listOf(
                ImageDetail(
                    imageRes = R.drawable.image_2,
                    comment = "Комментарий"
                )
            ),
            "Новосибирск",
            R.drawable.img_ava_3_selected,
            "Креативная фрида 3",
            "UX/UI-дизайнер, Веб-дизайнер",
            isOnline = true
        ),
        Feed(
            images = listOf(
                ImageDetail(
                    imageRes = R.drawable.image_3,
                    comment = "Очень-очень-очень-очень-очень-очень-очень-очень, максимально длинный комммммент."
                ),
                ImageDetail(
                    imageRes = R.drawable.image,
                    comment = "очень-очень-очень-очень-очень-очень, максимально длинный комммммент."
                ),
                ImageDetail(
                    imageRes = R.drawable.image_2,
                    comment = "Очень-очень-очень, максимально длинный комммммент."
                ),
                ImageDetail(
                    imageRes = R.drawable.image_3,
                    comment = "очень длинный комммммент."
                )
            ),
            city = "Санкт-Петербург",
            avatar = R.drawable.img_ava_6_selected,
            name = "дизайнер",
            specialization = "UX/UI дизайнер, Веб-дизайнер, продуктовый дизайнер",
            isOnline = true
        ),
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

        parentFragmentManager.setFragmentResultListener("fullscreen_result", viewLifecycleOwner) { _, bundle ->
            val action = bundle.getString("action")
            val feedPosition = bundle.getInt("feedPosition")
            lifecycleScope.launch {
                delay(400)
                when (action) {
                    "like", "skip" -> removeFeed(feedPosition)
                }
            }
        }

        viewPager = binding.viewPager

        adapter = FeedAdapter(
            feedList,
            onLike = { position ->
                removeFeed(position)
                     },
            onSkip = { position ->
                removeFeed(position)
                     },
            onImageClick = { imageList, imagePosition, feedPosition ->
                FullscreenImageDialog.newInstance(imageList, imagePosition, feedPosition)
                    .show(parentFragmentManager, "dialog")
            },
            fragment = this
        )

        binding.viewPager.adapter = adapter
        binding.viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL

        binding.icFilter.setOnClickListener {
            if (binding.bgTheEnd.isVisible) {
                NothingFilterDialog().show(parentFragmentManager, "NothingFilterDialog")
            } else findNavController().navigate(R.id.action_feedFragment_to_filterFeedFragment)
        }

    }

    private fun removeFeed(positionToRemove: Int) {
        if (feedList.isEmpty() || positionToRemove !in feedList.indices) return

        val wasLastItem = positionToRemove == feedList.lastIndex
        feedList.removeAt(positionToRemove)
        adapter.notifyItemRemoved(positionToRemove)

        if (wasLastItem || feedList.isEmpty()) {
            binding.viewPager.visibility = View.GONE
            binding.bgTheEnd.visibility = View.VISIBLE
            binding.textHorizontalScroll.visibility = View.VISIBLE
            startMarquee(binding.textDescription, binding.textHorizontalScroll, speed = 5000L)
        } else {
            binding.viewPager.setCurrentItem(positionToRemove.coerceAtMost(feedList.size - 1), false)
        }

        (activity as MainActivity).setUIVisibility(
            showHeader = true
        )

    }

}