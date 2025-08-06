package com.kednections.view.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kednections.databinding.ItemFullscreenPageBinding

class FullscreenPagerAdapter(
    private val items: List<ImageDetail>,
    private val onClose: () -> Unit
) : RecyclerView.Adapter<FullscreenPagerAdapter.PageViewHolder>() {

    inner class PageViewHolder(val binding: ItemFullscreenPageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFullscreenPageBinding.inflate(inflater, parent, false)
        return PageViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        val item = items[position]

        with(holder.binding) {
            imgFullScreens.setImageResource(item.imageRes)
            tvComment.text = item.comment
            btnClosed.setOnClickListener { onClose()}
        }
    }
}
