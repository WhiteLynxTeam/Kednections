package com.kednections.view.form.purposes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kednections.R
import com.kednections.databinding.ItemPurposeBinding

// Адаптер для отображения и выбора целей в фрагменте
class PurposesAdapter(
    private val context: Context, // Контекст для доступа к ресурсам
    private val onCheckChange: () -> Unit // Callback при изменении состояния checkbox
) : RecyclerView.Adapter<PurposesAdapter.PurposeViewHolder>() {

    // Список целей с их заголовками, описаниями и иконками
    private val purposes = listOf(
        PurposeItem(
            title = context.getString(R.string.looking_for_friends),
            description = context.getString(R.string.communicate_and_share_ideas),
            selectedIcon = R.drawable.ic_friends_selected,
            unselectedIcon = R.drawable.ic_friends
        ),
        PurposeItem(
            title = context.getString(R.string.looking_for_romance),
            description = context.getString(R.string.i_am_open_to_relationships),
            selectedIcon = R.drawable.ic_romance_selected,
            unselectedIcon = R.drawable.ic_romance
        ),
        PurposeItem(
            title = context.getString(R.string.looking_for_a_company),
            description = context.getString(R.string.for_walks_and_hikes_for_events),
            selectedIcon = R.drawable.ic_company_selected,
            unselectedIcon = R.drawable.ic_company
        ),
        PurposeItem(
            title = context.getString(R.string.looking_for_a_team),
            description = context.getString(R.string.i_want_to_join_the_project),
            selectedIcon = R.drawable.ic_looking_team_selected,
            unselectedIcon = R.drawable.ic_looking_team
        ),
        PurposeItem(
            title = context.getString(R.string.assembling_team),
            description = context.getString(R.string.looking_for_people_to_join_my_project),
            selectedIcon = R.drawable.ic_assembling_team_selected,
            unselectedIcon = R.drawable.ic_assembling_team
        )
    )

    // Массив для отслеживания состояния выбора каждой цели
    private val checkedStates = BooleanArray(purposes.size) { false }

    // ViewHolder для отображения отдельного элемента цели
    inner class PurposeViewHolder(val binding: ItemPurposeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // Привязка данных цели к элементам интерфейса
        fun bind(item: PurposeItem, position: Int) {
            // Установка заголовка и описания цели
            binding.text.text = item.title
            binding.tvDescription.text = item.description

            // Показываем дополнительные элементы для фрагмента
            binding.tvDescription.visibility = View.VISIBLE
            binding.checkboxPurpose.visibility = View.VISIBLE

            // Установка состояния checkbox
            binding.checkboxPurpose.isChecked = checkedStates[position]

            // Обновление внешнего вида элемента
            updateAppearance(checkedStates[position], item)

            // Обработчик клика на весь элемент
            binding.root.setOnClickListener {
                binding.checkboxPurpose.isChecked = !binding.checkboxPurpose.isChecked
            }

            // Обработчик изменения состояния checkbox
            binding.checkboxPurpose.setOnCheckedChangeListener { _, isChecked ->
                // Сохранение состояния выбора
                checkedStates[position] = isChecked
                // Обновление внешнего вида
                updateAppearance(isChecked, item)
                // Вызов callback для уведомления об изменении
                onCheckChange()
            }
        }

        // Обновление внешнего вида элемента в зависимости от состояния выбора
        private fun updateAppearance(isChecked: Boolean, item: PurposeItem) {
            // Изменение фона элемента
            binding.viewPurpose.setBackgroundResource(
                if (isChecked) R.drawable.bg_view_purpose_fragment else R.drawable.bg_auth_input
            )
            // Изменение иконки элемента
            binding.icon.setImageResource(
                if (isChecked) item.selectedIcon else item.unselectedIcon
            )
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
        holder.bind(purposes[position], position)
    }

    // Возвращает количество элементов в списке
    override fun getItemCount(): Int = purposes.size

    // Проверка, есть ли выбранные элементы
    fun hasCheckedItems(): Boolean {
        return checkedStates.any { it }
    }

    // Класс данных для представления цели
    data class PurposeItem(
        val title: String, // Заголовок цели
        val description: String, // Описание цели
        val selectedIcon: Int, // Иконка выбранной цели
        val unselectedIcon: Int // Иконка невыбранной цели
    )
}