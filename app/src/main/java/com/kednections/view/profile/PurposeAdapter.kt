package com.kednections.view.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kednections.databinding.ItemPurposeBinding
import com.kednections.domain.models.Tag

class PurposeAdapter(
    private val purpose: List<Tag> // Добавляем private val для создания свойства
) : RecyclerView.Adapter<PurposeAdapter.PurposeViewHolder>() {

    inner class PurposeViewHolder(private val binding: ItemPurposeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(purpose: Tag) {
            purpose.selectedIcon?.let { binding.icon.setImageResource(it) }
            binding.text.text = purpose.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurposeViewHolder {
        val binding = ItemPurposeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PurposeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PurposeViewHolder, position: Int) {
        holder.bind(purpose[position])
    }

    override fun getItemCount(): Int = purpose.size
}