package com.example.gamescoretracker

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.gamescoretracker.databinding.CurrentGameFragmentBinding
import com.example.gamescoretracker.model.GamesViewModel
import com.example.gamescoretracker.utils.LocalUtils
import com.example.gamescoretracker.utils.MILLIS_IN_SECOND

class CurrentGameFragment : Fragment() {

    private val sharedViewModel: GamesViewModel by activityViewModels()

    private var timer: CountDownTimer? = null

    private var _binding: CurrentGameFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)

    /**
     * Local variables to prevent constantly calling ViewModel
     * Not sure if this makes sense
     */

    private var t1Score: Int = 0
    private var t2Score: Int = 0

    private var duration: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(this) {
            cancelGame()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CurrentGameFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        restoreScores()
        setTexts()
        updateScoreBoard()
        setListeners()
        startTimer()
    }

    override fun onPause() {
        super.onPause()
        timer?.cancel()
        sharedViewModel.saveScores(t1Score, t2Score)
        sharedViewModel.saveDuration(duration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setListeners() {
        binding.tvIncreaseTeamOne.setOnClickListener {
            t1Score++
            updateScoreBoard()
        }
        binding.tvIncreaseTeamTwo.setOnClickListener {
            t2Score++
            updateScoreBoard()
        }
        binding.tvCancel.setOnClickListener {
            cancelGame()
        }
        binding.tvFinish.setOnClickListener {
            finishGame()
        }
    }

    private fun finishGame() {
        timer?.cancel()
        sharedViewModel.saveScores(t1Score, t2Score)
        findNavController().navigate(R.id.action_currentGameFragment_to_resultsFragment)
    }

    private fun cancelGame() {
        timer?.cancel()
        sharedViewModel.resetGame()
        findNavController().popBackStack(R.id.newGameFragment, false)
    }

    private fun setTexts() {
        binding.tvTeamOne.text = sharedViewModel.team1Name
        binding.tvTeamTwo.text = sharedViewModel.team2Name
    }

    private fun updateScoreBoard() {
        binding.tvScoreBoard.text =
            getString(
                R.string.score_board,
                t1Score,
                t2Score
            )
    }

    private fun restoreScores() {
        t1Score = sharedViewModel.team1Score
        t2Score = sharedViewModel.team2Score
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            sharedViewModel.gameDuration,
            MILLIS_IN_SECOND
        ) {
            override fun onTick(timeLeft: Long) {
                duration = timeLeft
                binding.tvTimer.text =
                    LocalUtils.getDurationString(timeLeft, context)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

}