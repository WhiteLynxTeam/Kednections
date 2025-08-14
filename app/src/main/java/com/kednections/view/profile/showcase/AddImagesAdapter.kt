package com.kednections.view.profile.showcase

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kednections.databinding.ItemAddButtonBinding
import com.kednections.databinding.ItemAddShowcaseImageBinding

class AddImagesAdapter(
    private val items: List<Uri>,
    private val itemWidth: Int,
    private val itemHeight: Int,
    private val onAddClick: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_IMAGE = 0
        private const val TYPE_ADD_BUTTON = 1
        const val MAX_ITEMS = 6
    }

    override fun getItemId(position: Int): Long {
        return when (getItemViewType(position)) {
            TYPE_ADD_BUTTON -> -1L // ID для кнопки
            else -> items[position].hashCode().toLong() // Уникальный ID для изображений
        }
    }

    override fun getItemCount(): Int = items.size + 1 // Кнопка всегда есть

    override fun getItemViewType(position: Int): Int {
        return if (position == items.size) TYPE_ADD_BUTTON else TYPE_IMAGE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ADD_BUTTON -> AddButtonViewHolder(
                ItemAddButtonBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ).apply {
                    root.layoutParams = RecyclerView.LayoutParams(itemWidth, itemHeight)
                }
            )
            else -> ImageViewHolder(
                ItemAddShowcaseImageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ).apply {
                    root.layoutParams = RecyclerView.LayoutParams(itemWidth, itemHeight)
                }
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AddButtonViewHolder -> {
                holder.binding.root.apply {
                    isEnabled = items.size < MAX_ITEMS // Блокируем если достигнут лимит
                    alpha = if (items.size < MAX_ITEMS) 1f else 0.5f // Визуальное отличие
                    setOnClickListener {
                        if (items.size < MAX_ITEMS) {
                            onAddClick()
                        }
                    }
                }
            }
            is ImageViewHolder -> {
                if (position < items.size) {
                    Glide.with(holder.itemView)
                        .load(items[position])
                        .override(itemWidth, itemHeight)
                        .centerCrop()
                        .into(holder.binding.imgItem)
                }
            }
        }
    }

    inner class AddButtonViewHolder(val binding: ItemAddButtonBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class ImageViewHolder(val binding: ItemAddShowcaseImageBinding) :
        RecyclerView.ViewHolder(binding.root)
}