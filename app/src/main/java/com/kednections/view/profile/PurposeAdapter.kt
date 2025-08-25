package com.kednections.view.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kednections.databinding.ItemPurposeBinding
import com.kednections.domain.models.profile.Purposes

class PurposeAdapter(
    private val purpose: List<Purposes>
) : RecyclerView.Adapter<PurposeAdapter.PurposeViewHolder>() {

    inner class PurposeViewHolder(private val binding: ItemPurposeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(purpose: Purposes) {
            binding.icon.setImageResource(purpose.icon)
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