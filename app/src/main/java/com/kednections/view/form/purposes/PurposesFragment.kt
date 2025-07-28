package com.kednections.view.form.purposes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kednections.R
import com.kednections.databinding.FragmentPurposesBinding
import com.kednections.utils.startMarquee
import com.kednections.view.activity.FormActivityViewModel
import dagger.android.support.AndroidSupportInjection

class PurposesFragment : Fragment() {

    private var _binding: FragmentPurposesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FormActivityViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPurposesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startMarquee(binding.textDescription, binding.textHorizontalScroll, speed = 5000L)

        val items = listOf(
            PurposeItem(
                view = binding.viewFriends,
                checkbox = binding.checkboxFriends,
                imageView = binding.ivFriends,
                selectedIcon = R.drawable.ic_friends_selected,
                unselectedIcon = R.drawable.ic_friends
            ),
            PurposeItem(
                view = binding.viewRomance,
                checkbox = binding.checkboxRomance,
                imageView = binding.ivRomance,
                selectedIcon = R.drawable.ic_romance_selected,
                unselectedIcon = R.drawable.ic_romance
            ),
            PurposeItem(
                view = binding.viewCompany,
                checkbox = binding.checkboxCompany,
                imageView = binding.ivCompany,
                selectedIcon = R.drawable.ic_company_selected,
                unselectedIcon = R.drawable.ic_company
            ),
            PurposeItem(
                view = binding.viewLookingTeam,
                checkbox = binding.checkboxLookingTeam,
                imageView = binding.ivLookingTeam,
                selectedIcon = R.drawable.ic_looking_team_selected,
                unselectedIcon = R.drawable.ic_looking_team
            ),
            PurposeItem(
                view = binding.viewAssemblingTeam,
                checkbox = binding.checkboxAssemblingTeam,
                imageView = binding.ivAssemblingTeam,
                selectedIcon = R.drawable.ic_assembling_team_selected,
                unselectedIcon = R.drawable.ic_assembling_team
            )
        )

        items.forEach { item ->
            item.checkbox.setOnCheckedChangeListener { _, isChecked ->
                item.view.setBackgroundResource(
                    if (isChecked) R.drawable.bg_view_purpose_fragment else R.drawable.bg_auth_input
                )
                item.imageView.setImageResource(
                    if (isChecked) item.selectedIcon else item.unselectedIcon
                )
                updateResumeButtonState()
            }
        }

        binding.btnResume.setOnClickListener {
            findNavController().navigate(R.id.action_purposesFragment_to_chooseCommunicateFragment)
        }
    }

    private fun updateResumeButtonState() {
        val isAnyChecked = listOf(
            binding.checkboxCompany.isChecked,
            binding.checkboxFriends.isChecked,
            binding.checkboxRomance.isChecked,
            binding.checkboxLookingTeam.isChecked,
            binding.checkboxAssemblingTeam.isChecked
        ).any { it }

        binding.btnResume.isEnabled = isAnyChecked
    }

    data class PurposeItem(
        val view: View,
        val checkbox: android.widget.CheckBox,
        val imageView: android.widget.ImageView,
        val selectedIcon: Int,
        val unselectedIcon: Int
    )

}