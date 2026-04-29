package com.moji.v1.ui.journal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.moji.v1.R
import com.moji.v1.data.DummyData
import com.moji.v1.databinding.FragmentJournalBinding
import com.moji.v1.model.JournalEntry
import com.moji.v1.model.Mood
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class JournalFragment : Fragment() {

    private var _binding: FragmentJournalBinding? = null
    private val binding get() = _binding!!

    private lateinit var currentMood: Mood

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJournalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val moodName = arguments?.getString("selectedMood") ?: Mood.NEUTRAL.name
        currentMood = Mood.valueOf(moodName)

        val journalBg = when (currentMood) {
            Mood.HAPPY -> R.drawable.bg_journal_happy
            Mood.NEUTRAL -> R.drawable.bg_journal_neutral
            Mood.SAD -> R.drawable.bg_journal_sad
            Mood.TIRED -> R.drawable.bg_journal_tired
            Mood.ANGRY -> R.drawable.bg_journal_angry
            Mood.ENVY -> R.drawable.bg_journal_envy
        }
        binding.cardJournal.setBackgroundResource(journalBg)

        val moodChar = when (currentMood) {
            Mood.HAPPY -> R.drawable.moji_happy
            Mood.NEUTRAL -> R.drawable.moji_neutral
            Mood.SAD -> R.drawable.moji_sad
            Mood.TIRED -> R.drawable.moji_tired
            Mood.ANGRY -> R.drawable.moji_angry
            Mood.ENVY -> R.drawable.moji_envy
        }
        binding.imgMoodBg.setImageResource(moodChar)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSave.setOnClickListener {
            saveJournal()
        }
    }

    private fun saveJournal() {
        val content = binding.etJournal.text.toString().trim()

        if (content.isEmpty()) {
            Toast.makeText(requireContext(), "Tulis dulu yuk!", Toast.LENGTH_SHORT).show()
            return
        }

        val now = Date()
        val displayFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val keyFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val newEntry = JournalEntry(
            id = DummyData.entries.size + 1,
            mood = currentMood,
            content = content,
            date = displayFormat.format(now),
            dateKey = keyFormat.format(now)
        )

        DummyData.entries.add(0, newEntry)

        Toast.makeText(requireContext(), "Jurnal tersimpan! 🎉", Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}