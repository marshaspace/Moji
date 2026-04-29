package com.moji.v1.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.moji.v1.R
import com.moji.v1.adapter.MoodAdapter
import com.moji.v1.databinding.FragmentHomeBinding
import com.moji.v1.model.Mood

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMoodCards()
    }

    private fun setupMoodCards() {
        val adapter = MoodAdapter(Mood.values().toList()) { mood ->
            val bundle = bundleOf("selectedMood" to mood.name)
            findNavController().navigate(R.id.journalFragment, bundle)
        }

        binding.rvMoodCards.layoutManager = GridLayoutManager(requireContext(), 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val totalItems = Mood.values().size
                    return if (totalItems % 2 != 0 && position == totalItems - 1) 2 else 1
                }
            }
        }
        binding.rvMoodCards.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}