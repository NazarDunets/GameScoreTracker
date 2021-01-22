package com.example.gamescoretracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.gamescoretracker.databinding.NewGameFragmentBinding
import com.example.gamescoretracker.model.GamesViewModel
import com.example.gamescoretracker.time_picker.TimePickerDialogFragment
import com.example.gamescoretracker.utils.LocalUtils
import com.google.android.material.textfield.TextInputLayout

const val DURATION_SET_ACTION = "DURATION_SET"
const val TIME_PICKER_TAG = "TIME_PIKER"

class NewGameFragment : Fragment() {

    private val sharedViewModel: GamesViewModel by activityViewModels()

    private var _binding: NewGameFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NewGameFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setWatchers()
        val filter = IntentFilter(DURATION_SET_ACTION)
        activity?.registerReceiver(durationSetReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        sharedViewModel.saveNames(
            binding.etTeam1.text.toString(),
            binding.etTeam2.text.toString()
        )
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        binding.etTeam1.setText(sharedViewModel.team1Name)
        binding.etTeam2.setText(sharedViewModel.team2Name)
        updateTimerText()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.unregisterReceiver(durationSetReceiver)
        _binding = null
    }

    private fun setListeners() {
        binding.tvGo.setOnClickListener {
            startGame()
        }
        binding.ibLeaderboard.setOnClickListener {
            goToLeaderboard()
        }
        binding.tvSetTimer.setOnClickListener {
            showTimePicker()
        }
    }

    private fun showTimePicker() {
        val timePicker = TimePickerDialogFragment.newInstance()
        activity?.let {
            timePicker.show(it.supportFragmentManager, TIME_PICKER_TAG)
        }
    }

    private fun goToLeaderboard() {
        findNavController().navigate(R.id.action_newGameFragment_to_leaderboardFragment)
    }

    private fun startGame() {
        if (isInputValid(binding.tfTeam1, binding.tfTeam2)) {
            sharedViewModel.saveNames(
                binding.etTeam1.text?.toString(),
                binding.etTeam2.text?.toString()
            )
            findNavController().navigate(R.id.action_newGameFragment_to_currentGameFragment)
        }
    }

    private fun updateTimerText() {
        binding.tvSetTimer.apply {
            text = LocalUtils.getDurationString(
                sharedViewModel.gameDuration,
                context
            )
        }
    }

    private fun setWatchers() {
        binding.etTeam1.addTextChangedListener(
            onTextChanged = { s, _, _, _ ->
                if (!s.isNullOrBlank()) binding.tfTeam1.error = null
            }
        )

        binding.etTeam2.addTextChangedListener(
            onTextChanged = { s, _, _, _ ->
                if (!s.isNullOrBlank()) binding.tfTeam2.error = null
            }
        )
    }

    private fun isInputValid(vararg fields: TextInputLayout): Boolean {
        var allValid = true
        fields.forEach {
            if (it.editText?.text.isNullOrBlank()) {
                it.error = getString(R.string.error_empty)
                allValid = false
            }
        }
        if (sharedViewModel.gameDuration.toInt() == 0) {
            Toast.makeText(
                context,
                getString(R.string.error_time),
                Toast.LENGTH_SHORT
            ).show()
            allValid = false
        }
        return allValid
    }

    private val durationSetReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateTimerText()
        }
    }

}