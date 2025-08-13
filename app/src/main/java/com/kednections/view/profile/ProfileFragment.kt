package com.kednections.view.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kednections.R
import com.kednections.databinding.FragmentProfileBinding
import com.kednections.domain.models.profile.Purposes
import dagger.android.support.AndroidSupportInjection

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var isProfileTop = true

    val purposesList = listOf(
        Purposes(R.drawable.ic_friends_selected, "ищу друзей"),
        Purposes(R.drawable.ic_romance_selected, "ищу романтику"),
        Purposes(R.drawable.ic_company_selected, "ищу компанию")
    )

    private val imageList = listOf(
        R.drawable.image,
        R.drawable.image_2,
        R.drawable.image,
        R.drawable.image_2,
        R.drawable.image,
        R.drawable.image_2,
        R.drawable.image,
        R.drawable.image_2,
        R.drawable.image,
        R.drawable.image_2,
        R.drawable.image,
        R.drawable.image_2,
    )

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val originalText = "Очень длинный текст который нужно обрезать"
        val maxLength = 20
        binding.tvName.text = originalText.substring(0, maxLength) + "..."

        val recyclerView = binding.rcViewImg
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = ShowCaseImageAdapter(imageList)

        if (purposesList.size == 1) {
            binding.viewPurposes.isEnabled = false
            binding.icPurposes.setImageResource(purposesList[0].icon)
            binding.tvPurposes.text = purposesList[0].description
        } else {
            binding.viewPurposes.isEnabled = true
        }

        binding.viewPurposes.setOnClickListener {
            val dialog = PurposeDialog.newInstance(purposesList)
            dialog.show(parentFragmentManager, "PurposesDialog")
        }

        binding.profileSwitcher.setOnClickListener {
            if (!isProfileTop) {
                toggleSwitcher()
            }
        }

        binding.designShowcaseSwitcher.setOnClickListener {
            if (isProfileTop) {
                toggleSwitcher()
            }
        }

        binding.btnEdit.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_showCaseFragment)
        }

    }

    private fun toggleSwitcher() {
        // Проверяем, какое изображение сейчас в состоянии "bottom"
        if (!isProfileTop) {
            // Клик по profile switcher (он в bottom состоянии)
            isProfileTop = true
            with(binding) {
                profileSwitcher.setImageResource(R.drawable.ic_profile_switcher_top)
                designShowcaseSwitcher.setImageResource(R.drawable.ic_design_showcase_switcher_bottom)
                viewBottom.setBackgroundResource(R.color.orange)
                btnBase.visibility = View.VISIBLE
                btnSpecialization.visibility = View.VISIBLE
                btnInterests.visibility = View.VISIBLE
                btnAbout.visibility = View.VISIBLE
                btnPortfolio.visibility = View.VISIBLE
                btnTests.visibility = View.VISIBLE
                btnSettings.visibility = View.VISIBLE
                btnEdit.visibility = View.GONE
                rcViewImg.visibility = View.GONE
            }
        } else {
            // Клик по design showcase switcher (он в bottom состоянии)
            isProfileTop = false
            with(binding) {
                profileSwitcher.setImageResource(R.drawable.ic_profile_switcher_bottom)
                designShowcaseSwitcher.setImageResource(R.drawable.ic_design_showcase_switcher_top)
                viewBottom.setBackgroundResource(R.color.background_color)
                btnBase.visibility = View.GONE
                btnSpecialization.visibility = View.GONE
                btnInterests.visibility = View.GONE
                btnAbout.visibility = View.GONE
                btnPortfolio.visibility = View.GONE
                btnTests.visibility = View.GONE
                btnSettings.visibility = View.GONE
                btnEdit.visibility = View.VISIBLE
                rcViewImg.visibility = View.VISIBLE
            }

        }
    }

}