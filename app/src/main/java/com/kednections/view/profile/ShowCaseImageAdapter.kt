package com.kednections.view.profile

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kednections.R
import com.kednections.databinding.ItemShowcaseImageBinding

class ShowCaseImageAdapter(
    private val imageUris: List<Uri>
) : RecyclerView.Adapter<ShowCaseImageAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemShowcaseImageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemShowcaseImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.binding.root.context)
            .load(imageUris[position])
            .into(holder.binding.imgItem)
    }

    override fun getItemCount() = imageUris.size
}