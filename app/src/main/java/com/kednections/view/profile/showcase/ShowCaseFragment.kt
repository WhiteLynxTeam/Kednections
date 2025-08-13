package com.kednections.view.profile.showcase

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kednections.databinding.FragmentShowCaseBinding
import com.kednections.utils.startMarquee
import com.kednections.view.activity.MainActivity
import dagger.android.support.AndroidSupportInjection

class ShowCaseFragment : Fragment() {

    private var _binding: FragmentShowCaseBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentShowCaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //бегущая строка (Анимация)
//        startMarquee(binding.textDescription, binding.textHorizontalScroll, speed = 4000L)
//        startMarquee(binding.textDescription2, binding.textHorizontalScroll2, speed = 6000L)

        (activity as MainActivity).setUIVisibility(
            showHeader = false
        )

    }

}