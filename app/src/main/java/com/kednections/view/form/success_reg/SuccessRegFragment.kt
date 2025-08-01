package com.kednections.view.form.success_reg

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kednections.databinding.FragmentSuccessRegBinding
import com.kednections.view.activity.FormActivity
import dagger.android.support.AndroidSupportInjection

class SuccessRegFragment : Fragment() {

    private var _binding: FragmentSuccessRegBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSuccessRegBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as FormActivity).setUIVisibility(
            showHeader = false
        )

    }

}