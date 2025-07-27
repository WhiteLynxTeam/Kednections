package com.kednections.view.form.geolocation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import com.kednections.R
import com.kednections.databinding.FragmentAvatarBinding
import com.kednections.databinding.FragmentGeolocationBinding
import com.kednections.utils.startMarquee
import com.kednections.view.activity.FormActivityViewModel
import dagger.android.support.AndroidSupportInjection
import kotlin.getValue

class GeolocationFragment : Fragment() {

    private var _binding: FragmentGeolocationBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGeolocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //бегущая строка (Анимация)
        startMarquee(binding.textDescription, binding.textHorizontalScroll, speed = 5000L)
    }

}