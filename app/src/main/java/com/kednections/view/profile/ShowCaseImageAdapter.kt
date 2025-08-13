package com.kednections.view.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kednections.databinding.ItemShowcaseImageBinding

class ShowCaseImageAdapter(
    private val images: List<Int>,
//    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<ShowCaseImageAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(private val binding: ItemShowcaseImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

//        init {
//            binding.root.setOnClickListener {
//                onItemClick(adapterPosition)
//            }
//        }

        fun bind(imageRes: Int) {
            binding.imgItem.setImageResource(imageRes)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemShowcaseImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int = images.size
}