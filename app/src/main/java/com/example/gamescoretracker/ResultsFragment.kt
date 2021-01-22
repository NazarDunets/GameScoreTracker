package com.example.gamescoretracker

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.gamescoretracker.databinding.ResultsFragmentBinding
import com.example.gamescoretracker.model.GamesViewModel
import com.example.gamescoretracker.model.Winner

class ResultsFragment : Fragment() {

    private val sharedViewModel: GamesViewModel by activityViewModels()

    private var _binding: ResultsFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(this) {
            goToNewGame()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ResultsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setListeners() {
        binding.tvLeaderboard.setOnClickListener {
            goToLeaderboard()
        }
        binding.tvShare.setOnClickListener {
            shareGame()
        }
    }

    private fun goToLeaderboard() {
        sharedViewModel.resetGame()
        findNavController().navigate(R.id.action_resultsFragment_to_leaderboardFragment)
    }

    private fun goToNewGame() {
        sharedViewModel.resetGame()
        findNavController().popBackStack(R.id.newGameFragment, false)
    }

    private fun shareGame() {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                getString(
                    R.string.share_msg,
                    sharedViewModel.team1Name,
                    sharedViewModel.team2Name,
                    sharedViewModel.team1Score,
                    sharedViewModel.team2Score
                )
            )
            type = "text/plain"
        }

        val shareIntent = Intent
            .createChooser(sendIntent, getString(R.string.share_title))
        startActivity(shareIntent)
    }

    private fun setupViews() {
        val t1Name = sharedViewModel.team1Name
        val t2Name = sharedViewModel.team2Name

        binding.tvTeamOneResult.text = t1Name
        binding.tvTeamTwoResult.text = t2Name

        val t1Score = sharedViewModel.team1Score
        val t2Score = sharedViewModel.team2Score

        binding.tvResultsScore.text =
            getString(R.string.score_board, t1Score, t2Score)

        when {
            t1Score == t2Score -> {
                binding.ivResult.setImageResource(R.drawable.ic_handshake)
            }
            t1Score > t2Score -> {
                TextViewCompat.setTextAppearance(binding.tvTeamTwoResult, R.style.LoserTeamText)
                sharedViewModel.saveWinner(Winner(t1Name, t1Score))
            }
            else -> {
                TextViewCompat.setTextAppearance(binding.tvTeamOneResult, R.style.LoserTeamText)
                sharedViewModel.saveWinner(Winner(t2Name, t2Score))
            }
        }

    }

}