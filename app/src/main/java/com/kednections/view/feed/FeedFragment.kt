package com.kednections.view.feed

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.kednections.R
import com.kednections.databinding.FragmentFeedBinding
import com.kednections.utils.startMarquee
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
            city = "Москва",
            avatar = R.drawable.img_ava_1,
            name = "Дизайнер",
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
            R.drawable.img_ava_2_selected,
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
            R.drawable.img_ava_3,
            "Креативная фрида 3",
            "UX/UI-дизайнер, Веб-дизайнер",
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
                delay(2000)
                when (action) {
                    "like" -> removeFeed(feedPosition)
                    "skip" -> removeFeed(feedPosition)
                }
            }
        }

        viewPager = binding.viewPager

        adapter = FeedAdapter(
            feedList,
            onLike = { position -> removeFeed(position) },
            onSkip = { position -> removeFeed(position) },
            onImageClick = { imageList, position ->
                FullscreenImageDialog.newInstance(imageList, position)
                    .show(parentFragmentManager, "dialog")
            },
            fragment = this
        )



//        parentFragmentManager.setFragmentResultListener("fullscreen_result", viewLifecycleOwner) { _, bundle ->
//            when (bundle.getString("action")) {
//                "like" -> removeFeed(bundle.getInt("feedPosition"))
//                "skip" -> removeFeed(bundle.getInt("feedPosition"))
//            }
//        }

        binding.viewPager.adapter = adapter
        binding.viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL

        binding.icFilter.setOnClickListener {
            if (binding.bgTheEnd.isVisible) {
                NothingFilterDialog().show(parentFragmentManager, "NothingFilterDialog")
            } //else findNavController().navigate(R.id.action_feedFragment_to_filterFeedFragment)
        }

    }

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

}