package com.example.gamescoretracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gamescoretracker.databinding.WinnerItemBinding
import com.example.gamescoretracker.model.Winner
import com.example.gamescoretracker.utils.WinnersDiffCallback

class LeaderboardAdapter : RecyclerView.Adapter<WinnerViewHolder>() {

    private var items: List<Winner> = emptyList()

    fun setItems(newItems: List<Winner>) {
        items = newItems
        notifyDataSetChanged()
    }

    fun updateItems(newItems: List<Winner>) {
        val diffResult = DiffUtil.calculateDiff(WinnersDiffCallback(items, newItems))
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WinnerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.winner_item, parent, false)

        return WinnerViewHolder(view)
    }

    override fun onBindViewHolder(holder: WinnerViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}

class WinnerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = WinnerItemBinding.bind(itemView)

    fun bind(winner: Winner) {
        binding.tvWinnerName.text = winner.name
        binding.tvWinnerScore.text = winner.score.toString()
    }
}