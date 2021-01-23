package com.example.gamescoretracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamescoretracker.databinding.LeaderboardFragmentBinding
import com.example.gamescoretracker.model.GamesViewModel

class LeaderboardFragment : Fragment() {

    private val sharedViewModel: GamesViewModel by activityViewModels()

    private var _binding: LeaderboardFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)

    private lateinit var leaderboardAdapter: LeaderboardAdapter

    private var isSorted = false

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
        _binding = LeaderboardFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        setListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setListeners() {
        binding.tvNewGame.setOnClickListener {
            goToNewGame()
        }
        binding.ibClear.setOnClickListener {
            clearLeaderboard()
        }
        binding.ibSort.setOnClickListener {
            changeSorting()
        }
    }

    private fun setupRecycler() {
        leaderboardAdapter = LeaderboardAdapter().apply {
            setItems(sharedViewModel.winners)
        }

        binding.rvWinners.apply {
            adapter = leaderboardAdapter
            addItemDecoration(
                DividerItemDecoration(context, RecyclerView.VERTICAL)
            )
            layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }

    }

    private fun clearLeaderboard() {
        sharedViewModel.clearWinnersList()
        leaderboardAdapter.setItems(sharedViewModel.winners)
    }

    private fun changeSorting() {
        if (isSorted) {
            leaderboardAdapter.updateItems(sharedViewModel.winners)
        } else {
            val sortedByScore = sharedViewModel.winners.sortedByDescending {it.score}
            leaderboardAdapter.updateItems(sortedByScore)
        }
        isSorted = !isSorted
    }

    private fun goToNewGame() {
        findNavController().popBackStack(R.id.newGameFragment, false)
    }

}