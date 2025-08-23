package com.kednections.view.form.purposes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kednections.R
import com.kednections.databinding.ItemPurposeBinding
import com.kednections.domain.models.Tag

class PurposesAdapter(
    private val onCheckChange: () -> Unit
) : RecyclerView.Adapter<PurposesAdapter.PurposeViewHolder>() {

    private var tags: MutableList<Tag> = mutableListOf()

    inner class PurposeViewHolder(private val binding: ItemPurposeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Tag, position: Int) {
            binding.text.text = item.title
            binding.tvDescription.text = item.description
            binding.checkboxPurpose.isChecked = item.isChecked

            // Изменение фона элемента
            binding.viewPurpose.setBackgroundResource(
                if (item.isChecked) R.drawable.bg_view_purpose_fragment else R.drawable.bg_auth_input
            )

            // Изменение иконки элемента
            (if (item.isChecked) item.selectedIcon else item.unselectedIcon)?.let {
                binding.icon.setImageResource(
                    it
                )
            }

            // Обработчик клика на весь элемент
            binding.root.setOnClickListener {
                item.isChecked = !item.isChecked
                notifyItemChanged(position)
                onCheckChange()
            }
        }
    }

    // Создание нового ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurposeViewHolder {
        val binding = ItemPurposeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PurposeViewHolder(binding)
    }

    // Привязка данных к ViewHolder на определенной позиции
    override fun onBindViewHolder(holder: PurposeViewHolder, position: Int) {
        holder.bind(tags[position], position)
    }

    // Возвращает количество элементов в списке
    override fun getItemCount(): Int = tags.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(updateTags: List<Tag>) {
        this.tags = updateTags.toMutableList()
        notifyDataSetChanged()
    }
}