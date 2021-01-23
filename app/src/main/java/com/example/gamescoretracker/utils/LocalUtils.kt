package com.example.gamescoretracker.utils

import android.content.Context
import com.example.gamescoretracker.R

const val MILLIS_IN_MINUTE: Long = 60_000
const val MILLIS_IN_SECOND: Long = 1000

object LocalUtils {

    fun getDurationString(duration: Long, ctx: Context?): String {
        val minutes = (duration / MILLIS_IN_MINUTE)
            .toString().padStart(2, '0')
        val seconds = (duration % MILLIS_IN_MINUTE / MILLIS_IN_SECOND)
            .toString().padStart(2, '0')

        return ctx?.getString(R.string.timer_text, minutes, seconds) ?: "00:00"
    }

    fun durationToMillis(minutes: Int, seconds: Int): Long {
        return (minutes * MILLIS_IN_MINUTE + seconds * MILLIS_IN_SECOND)
    }
}