package com.kednections.view.feed.filter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.kednections.R
import com.kednections.databinding.FragmentFilterFeedBinding
import dagger.android.support.AndroidSupportInjection

class FilterFeedFragment : Fragment() {

    private var _binding: FragmentFilterFeedBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFilterFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val specializationIds = listOf(
            binding.tvArtDirector, binding.tvIllustrator, binding.tv3dDesigner, binding.tvWebDesigner, binding.tvGameDesigner, binding.tvArtist, binding.tvGraphicDesigner, binding.tvInteriorDesigner, binding.tvUiDesigner, binding.tvFashionDesigner, binding.tvPrintDesigner, binding.tvPresentationDesigner, binding.tvCommunicationDesigner, binding.tvBrandDesigner,
            binding.tvLandscapeDesigner, binding.tvDesigner, binding.tvProductDesigner, binding.tvUxUiDesigner, binding.tvIndustrialDesigner, binding.tvFpxDesigner, binding.tvAiDesigner, binding.tvArVrDesigner, binding.tvMotionDesigner, binding.tvSmmDesigner
        )
    }
}