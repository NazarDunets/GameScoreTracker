package com.example.gamescoretracker.time_picker

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.gamescoretracker.DURATION_SET_ACTION
import com.example.gamescoretracker.databinding.TimePickerDialogBinding
import com.example.gamescoretracker.model.GamesViewModel
import com.example.gamescoretracker.utils.LocalUtils

class TimePickerDialogFragment : DialogFragment() {

    companion object {
        fun newInstance() = TimePickerDialogFragment()
    }

    private val sharedViewModel: GamesViewModel by activityViewModels()

    private var _binding: TimePickerDialogBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TimePickerDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setListeners() {
        binding.tvDialogPositive.setOnClickListener {
            setDuration()
        }
    }

    private fun setDuration() {
        var mins = 0
        var secs = 0

        if (!binding.etMin.text.isNullOrBlank())
            mins = binding.etMin.text.toString().toInt()

        if (!binding.etSec.text.isNullOrBlank())
            secs = binding.etSec.text.toString().toInt()

        val gameDuration = LocalUtils.durationToMillis(mins, secs)
        sharedViewModel.saveDuration(gameDuration)

        val intent = Intent().apply {
            action = DURATION_SET_ACTION
        }
        context?.sendBroadcast(intent)
        dialog?.dismiss()
    }
}