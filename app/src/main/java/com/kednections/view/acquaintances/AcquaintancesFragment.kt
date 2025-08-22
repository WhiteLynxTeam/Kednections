package com.kednections.view.acquaintances

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import com.kednections.core.base.BaseFragment
import com.kednections.databinding.FragmentAcquaintancesBinding
import com.kednections.view.activity.MainActivity

class AcquaintancesFragment : BaseFragment<FragmentAcquaintancesBinding>() {

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAcquaintancesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {}

        (activity as MainActivity).setUIVisibility(showBottom = true)
    }

}