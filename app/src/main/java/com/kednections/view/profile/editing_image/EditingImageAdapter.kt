package com.kednections.view.profile.editing_image

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kednections.databinding.ItemEditingImageBinding

class EditingImageAdapter(
    private val onItemClick: (Uri, Int) -> Unit
) : RecyclerView.Adapter<EditingImageAdapter.ViewHolder>() {

    private val items = mutableListOf<Uri>()
    private var selectedPosition = RecyclerView.NO_POSITION

    fun setItems(newItems: List<Uri>, position: Int = 0) {
        items.clear()
        items.addAll(newItems)
        selectedPosition = if (items.isNotEmpty() && position in items.indices) {
            position
        } else if (items.isNotEmpty()) {
            0
        } else {
            RecyclerView.NO_POSITION
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemEditingImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(uri: Uri, isSelected: Boolean) {
            Glide.with(binding.root)
                .load(uri)
                .centerCrop()
                .into(binding.imgItem)

            binding.cvImage.isSelected = isSelected

            binding.root.setOnClickListener {
                val oldPos = selectedPosition
                selectedPosition = bindingAdapterPosition
                notifyItemChanged(oldPos)
                notifyItemChanged(selectedPosition)
                onItemClick(uri, selectedPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEditingImageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position == selectedPosition)
    }

    override fun getItemCount(): Int = items.size
}
