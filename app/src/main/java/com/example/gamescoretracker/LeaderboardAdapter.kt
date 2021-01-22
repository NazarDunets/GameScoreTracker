package com.example.gamescoretracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gamescoretracker.model.Winner

class LeaderboardAdapter : RecyclerView.Adapter<WinnerViewHolder>() {

    private var items: List<Winner> = emptyList()
    private lateinit var alternativeSorting: List<Winner>

    fun setItems(i: List<Winner>) {
        items = i
        alternativeSorting = items.sortedByDescending { w -> w.score }
        notifyDataSetChanged()
    }

    fun changeSorting() {
        items = alternativeSorting.also { alternativeSorting = items }
        notifyDataSetChanged()
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

    private val tvWinnerName = itemView.findViewById<TextView>(R.id.tvWinnerName)
    private val tvWinnerScore = itemView.findViewById<TextView>(R.id.tvWinnerScore)

    fun bind(winner: Winner) {
        tvWinnerName.text = winner.name
        tvWinnerScore.text = winner.score.toString()
    }
}