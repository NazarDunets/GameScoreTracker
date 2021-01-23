package com.example.gamescoretracker.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.gamescoretracker.model.Winner

class WinnersDiffCallback(private val oldItems: List<Winner>, private val newItems: List<Winner>) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].id == newItems[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }

}
