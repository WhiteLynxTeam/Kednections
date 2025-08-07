package com.kednections.view.feed

import android.graphics.Color
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.kednections.R
import com.kednections.databinding.ItemFeedBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.core.view.isVisible
import com.kednections.domain.models.feed.Feed
import com.kednections.domain.models.feed.ImageDetail
import java.util.Locale

class FeedAdapter(
    val items: MutableList<Feed>,
    private val onLike: (Int) -> Unit,
    private val onSkip: (Int) -> Unit,
    private val onImageClick: (List<ImageDetail>, Int, Int) -> Unit,
    private val fragment: Fragment
) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    inner class FeedViewHolder(val binding: ItemFeedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

        fun setupImagesProgress(total: Int) {
            binding.imagesProgressContainer.removeAllViews()

            // Показываем прогресс-бар только если картинок больше одной
            binding.imagesProgressContainer.visibility = if (total > 1) View.VISIBLE else View.GONE

            if (total > 1) {
                repeat(total) {
                    val segment = View(fragment.requireContext()).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            0,
                            dpToPx(4),
                            1f
                        ).apply {
                            marginEnd = if (it < total - 1) dpToPx(8) else 0
                        }
                        setBackgroundColor(Color.LTGRAY)
                    }
                    binding.imagesProgressContainer.addView(segment)
                }
            }
        }

        private fun dpToPx(dp: Int): Int {
            return (dp * fragment.resources.displayMetrics.density).toInt()
        }

        fun updateImagesProgress(current: Int) {
            // Проверяем видимость контейнера
            if (binding.imagesProgressContainer.isVisible) {
                for (i in 0 until binding.imagesProgressContainer.childCount) {
                    binding.imagesProgressContainer.getChildAt(i).setBackgroundColor(
                        if (i <= current) Color.WHITE else Color.argb(128, 255, 255, 255)
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFeedBinding.inflate(inflater, parent, false)
        return FeedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {

        val item = items[position]
        //var isSubscribe = item.isSubscribe
        with(holder.binding) {
            holder.setupImagesProgress(item.images.size)

            imgFeed.adapter = ImagePagerAdapter(item.images) { imagePosition ->
                holder.updateImagesProgress(imagePosition)
                onImageClick(item.images, imagePosition, holder.adapterPosition) // Добавляем holder.adapterPosition
            }

            if (item.images.size > 1) {
                imgFeed.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(imagePosition: Int) {
                        holder.updateImagesProgress(imagePosition)
                    }

                    override fun onPageScrollStateChanged(state: Int) {}
                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {}
                })
            }

            tvGeo.text = item.city.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            }
            imgAvatar.setImageResource(item.avatar)
            tvName.text = item.name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            }
            tvSpecialization.text = item.specialization

            if (item.isOnline) isOnline.visibility = View.VISIBLE
            else isOnline.visibility = View.GONE
//            if (isSubscribe) {
//                tvSubscribe.text = "отписаться"
//                tvSubscribeDone.text = "отписка done"
//            } else {
//                tvSubscribe.text = "подписаться"
//                tvSubscribeDone.text = "подписка done"
//            }

            val context = holder.itemView.context

            icOverMenu.setOnClickListener {
                viewPopUp.visibility = View.VISIBLE
                overlay.visibility = View.VISIBLE

                overlay.setOnClickListener {
                    viewPopUp.visibility = View.GONE
                    overlay.visibility = View.GONE
                }
            }

            write.setOnClickListener {
                holder.scope.launch {
                    //tvWriteClick.visibility = View.VISIBLE
                    delay(300)
                    viewPopUp.visibility = View.GONE
                    //delay(1700)
                    //tvWriteClick.visibility = View.GONE
                }
            }

            subscribe.setOnClickListener {
//                isSubscribe = !isSubscribe
//                item.isSubscribe = isSubscribe // Обновляем данные в item

//                // Обновляем текст
//                if (isSubscribe) {
//                    tvSubscribe.text = "отписаться"
//                    tvSubscribeDone.text = "подписка done"
//                } else {
//                    tvSubscribe.text = "подписаться"
//                    tvSubscribeDone.text = "отписка done"
//                }

                holder.scope.launch {
                    tvSubscribeDone.visibility = View.VISIBLE
                    delay(300)
                    viewPopUp.visibility = View.GONE
                    delay(1500)
                    tvSubscribeDone.visibility = View.GONE
                }
            }

            btnLike.setOnClickListener {
                val currentPos = holder.adapterPosition
                holder.scope.launch {
                    btnLike.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_button_like_for_feed_pressed
                        )
                    )
                    delay(150)
                    btnLike.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_button_like_for_feed
                        )
                    )
                    if (currentPos != RecyclerView.NO_POSITION) {
                        onLike(currentPos)
                    }
                }

            }
            btnSkip.setOnClickListener {
                val currentPos = holder.adapterPosition
                holder.scope.launch {
                    btnSkip.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_button_skip_for_feed_pressed
                        )
                    )
                    delay(150)
                    btnSkip.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_button_skip_for_feed
                        )
                    )
                    if (currentPos != RecyclerView.NO_POSITION) {
                        onSkip(currentPos)
                    }
                }
            }

        }
    }

    override fun getItemCount(): Int = items.size

}