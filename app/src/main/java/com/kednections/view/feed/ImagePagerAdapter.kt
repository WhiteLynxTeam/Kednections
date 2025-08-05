package com.kednections.view.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.kednections.R

class ImagePagerAdapter(
    private val images: List<ImageDetail>,
    private val onImageClick: (Int) -> Unit
) : RecyclerView.Adapter<ImagePagerAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dialog_fullscreen_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])
        holder.itemView.setOnClickListener {
            onImageClick(position) // Передаем позицию изображения
        }
    }

    override fun getItemCount() = images.size

    inner class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.imageView)

        fun bind(imageDetail: ImageDetail) {
            imageView.setImageResource(imageDetail.imageRes)

        }
    }
}