package com.example.gamescoretracker.model

import java.util.*

data class Winner(val name: String, val score: Int) {
    val id: UUID = UUID.randomUUID()
}