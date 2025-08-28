package com.kednections.view.profile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.kednections.R
import com.kednections.core.base.BaseFragment
import com.kednections.databinding.FragmentProfileBinding
import com.kednections.domain.models.Ava
import com.kednections.domain.models.NameOrNick
import com.kednections.utils.decodeStringToBitmap
import com.kednections.utils.startMarquee
import com.kednections.view.activity.MainActivity
import com.kednections.view.activity.MainActivityViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val activityViewModel: MainActivityViewModel by activityViewModels()
    private lateinit var profileViewModel: ProfileViewModel
    private val imageUris = mutableListOf<Uri>()
    private var isProfileTop = true

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
            activityViewModel.user.collect { user ->
                if (user != null) {
                    if (user.photo != null) {
//                        binding.imgAvatar.setImageBitmap(decodeStringToBitmap(user.photo!!.photo))
                        val bitmap = decodeStringToBitmap(user.photo!!.photo)
                        Glide.with(binding.imgAvatar.context)
                            .load(bitmap)
                            .into(binding.imgAvatar)
                    } else {
                        binding.imgAvatar.setImageResource(Ava.fromName(user.status).imgResSelected)
                    }

                    val maxLength = 20
                    binding.tvName.text = when (user.nameOrNick) {
                        NameOrNick.NAME -> {
                            if (user.fio != null && user.fio.length > maxLength) {
                                user.fio.substring(0, maxLength) + "..."
                            } else {
                                user.fio ?: ""
                            }
                        }

                        NameOrNick.NICK -> {
                            if (user.nick != null && user.nick.length > maxLength) {
                                user.nick.substring(0, maxLength) + "..."
                            } else {
                                user.nick ?: ""
                            }
                        }
                    }

                    binding.tvGeo.text = user.city.name

                    when (user.specializations.size) {
                        1 -> binding.tvSpecializations.text = user.specializations[0].name
                        2 -> binding.tvSpecializations.text =
                            "${user.specializations[0].name} \u25CF ${user.specializations[1].name}"

                        3 -> binding.tvSpecializations.text =
                            "${user.specializations[0].name} \u25CF ${user.specializations[1].name}\n${user.specializations[2].name}"
                    }

                    if (user.tags.size <= 1) {
                        binding.viewPurposes.isEnabled = false
                        user.tags[0].selectedIcon?.let { binding.icPurposes.setImageResource(it) }
                        binding.tvPurposes.text = user.tags[0].title
                    } else {
                        binding.viewPurposes.isEnabled = true
                    }

                    //[red] это позор стереть, после того как бэк будет выдавать все.
                    binding.icConnection.setImageResource(
                        if ("онлайн" in user.communicationMethod.name)
                            R.drawable.ic_online_selected else R.drawable.ic_offline_selected
                    )
                    binding.tvConnection.text = user.communicationMethod.name
                }
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

        val recyclerView = binding.rcViewImg
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        with(binding) {
            viewPurposes.setOnClickListener {
                if (activityViewModel.user.value?.tags?.isNotEmpty() == true) {
                    lifecycleScope.launch {
                        binding.viewPurposes.setBackgroundResource(R.drawable.bg_my_purposes_pressed)
                        delay(200)
                        val dialog = PurposeDialog.newInstance()
                        dialog.show(parentFragmentManager, "PurposesDialog")
                        binding.viewPurposes.setBackgroundResource(R.drawable.bg_my_purposes)
                    }
                }
            }

            profileSwitcher.setOnClickListener {
                if (!isProfileTop) {
                    toggleSwitcher()
                }
            }

            designShowcaseSwitcher.setOnClickListener {
                if (isProfileTop) {
                    toggleSwitcher()
                }
            }

            btnEdit.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_showCaseFragment)
            }

            imgAvatar.setOnClickListener {
                viewPopUpAvatar.visibility = View.VISIBLE
                overlay.visibility = View.VISIBLE
                overlayHeader.visibility = View.VISIBLE

                overlay.setOnClickListener {
                    viewPopUpAvatar.visibility = View.GONE
                    overlay.visibility = View.GONE
                    overlayHeader.visibility = View.GONE
                }
                overlayHeader.setOnClickListener {
                    viewPopUpAvatar.visibility = View.GONE
                    overlay.visibility = View.GONE
                    overlayHeader.visibility = View.GONE
                }
            }

            chooseAvatar.setOnClickListener {
                lifecycleScope.launch {
                    delay(300)
                    viewPopUpAvatar.visibility = View.GONE
                    overlay.visibility = View.GONE
                    overlayHeader.visibility = View.GONE
                    findNavController().navigate(R.id.action_profileFragment_to_choosingAvatarFragment)
                }
            }

            uploadPhoto.setOnClickListener {
                //pickPhotoLauncher.launch("image/*")
                lifecycleScope.launch {
                    delay(300)
                    viewPopUpAvatar.visibility = View.GONE
                    overlay.visibility = View.GONE
                    overlayHeader.visibility = View.GONE
                }
            }

            uploadPhoto.setOnClickListener {
                lifecycleScope.launch {
                    delay(300)
                    viewPopUpAvatar.visibility = View.GONE
                    overlay.visibility = View.GONE
                    overlayHeader.visibility = View.GONE
                }
            }

            takePhoto.setOnClickListener {
                lifecycleScope.launch {
                    delay(300)
                    viewPopUpAvatar.visibility = View.GONE
                    overlay.visibility = View.GONE
                    overlayHeader.visibility = View.GONE
                }
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {}

        (activity as MainActivity).setUIVisibility(showBottom = true)

        activityViewModel.getUser()

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