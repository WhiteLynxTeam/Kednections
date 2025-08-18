package com.kednections.view.profile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kednections.R
import com.kednections.core.base.BaseFragment
import com.kednections.databinding.FragmentProfileBinding
import com.kednections.domain.models.profile.Purposes
import com.kednections.utils.decodeStringToBitmap
import com.kednections.utils.startMarquee
import com.kednections.view.activity.MainActivity
import com.kednections.view.activity.MainActivityViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val activityViewModel: MainActivityViewModel by activityViewModels()
    private lateinit var profileViewModel: ProfileViewModel
    private val imageUris = mutableListOf<Uri>()
    private var isProfileTop = true

    val specializationList = listOf(
        "UX/UI-дизайнер",
        "Веб-дизайнер",
        "графический дизайнер"
    )

    val purposesList = listOf(
        Purposes(R.drawable.ic_friends_selected, "ищу друзей"),
        Purposes(R.drawable.ic_romance_selected, "ищу романтику"),
        Purposes(R.drawable.ic_company_selected, "ищу компанию")
    )

//    private val imageList = listOf(
//        R.drawable.image,
//        R.drawable.image_2,
//        R.drawable.image,
//        R.drawable.image_2,
//        R.drawable.image
//    )

    @Inject
    lateinit var vmFactory: ProfileViewModel.Factory

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentProfileBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel =
            ViewModelProvider(this, vmFactory)[ProfileViewModel::class.java]

        viewLifecycleOwner.lifecycleScope.launch {
            profileViewModel.photo.collect {
                println("ProfileFragment photo=$it")
                binding.imgAvatar.setImageBitmap(decodeStringToBitmap(it))
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            activityViewModel.selectedImages.collect { uris ->
                uris.let {
                    imageUris.clear()
                    imageUris.addAll(it)
                    binding.rcViewImg.adapter = ShowCaseImageAdapter(imageUris)
                }

            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            activityViewModel.isProfileTop.collect { isTop ->
                isProfileTop = isTop
                updateUI() // Обновляем UI в соответствии с новым состоянием
            }
        }

        val originalText = "Очень длинный текст который нужно обрезать"
        val maxLength = 20
        binding.tvName.text = originalText.substring(0, maxLength) + "..."

        val recyclerView = binding.rcViewImg
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

/*        profileViewModel.selectedImages.observe(viewLifecycleOwner) { uris ->
            uris?.let {
                imageUris.clear()
                imageUris.addAll(it)
                recyclerView.adapter = ShowCaseImageAdapter(imageUris)
            }
        }
        profileViewModel.isProfileTop.observe(viewLifecycleOwner) { isTop ->
            isProfileTop = isTop
            updateUI() // Обновляем UI в соответствии с новым состоянием
        }*/

        when (specializationList.size) {
            1 -> binding.tvSpecializations.text = "${ specializationList[0] }"
            2 -> binding.tvSpecializations.text = "${specializationList[0]} \u25CF ${specializationList[1]}"
            3 -> binding.tvSpecializations.text = "${specializationList[0]} \u25CF ${specializationList[1]}\n${specializationList[2]}"
        }

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

        (activity as MainActivity).setUIVisibility(showHeader = true)

        profileViewModel.getUser()

    }

    private fun toggleSwitcher() {
        isProfileTop = !isProfileTop
        updateUI()
    }

    private fun updateUI() {
        with(binding) {
            if (isProfileTop) {
                // Настройки для режима Profile
                profileSwitcher.setImageResource(R.drawable.ic_profile_switcher_top)
                imgBgDescription.setImageResource(R.drawable.bg_profile)
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
                bgShowcaseNull.visibility = View.GONE
                textHorizontalScroll.visibility = View.GONE
            } else {
                // Настройки для режима Showcase
                profileSwitcher.setImageResource(R.drawable.ic_profile_switcher_bottom)
                imgBgDescription.setImageResource(R.drawable.bg_profile_2)
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

                // Дополнительные условия для Showcase
                if (imageUris.isEmpty()) {
                    rcViewImg.visibility = View.GONE
                    bgShowcaseNull.visibility = View.VISIBLE
                    textHorizontalScroll.visibility = View.VISIBLE
                    startMarquee(textDescription, textHorizontalScroll, speed = 5000L)
                } else {
                    rcViewImg.visibility = View.VISIBLE
                    bgShowcaseNull.visibility = View.GONE
                    textHorizontalScroll.visibility = View.GONE
                }
            }

        }
    }

}