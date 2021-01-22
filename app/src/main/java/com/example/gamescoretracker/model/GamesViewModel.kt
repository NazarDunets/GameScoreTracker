package com.example.gamescoretracker.model

import androidx.lifecycle.ViewModel

class GamesViewModel : ViewModel() {

    val winners: List<Winner> get() = _winners

    val team1Name get() = _team1Name
    val team2Name get() = _team2Name

    val team1Score get() = _team1Score
    val team2Score get() = _team2Score

    val gameDuration get() = _gameDuration

    private val _winners: MutableList<Winner> = mutableListOf()

    private var _team1Name: String = ""
    private var _team2Name: String = ""

    private var _team1Score: Int = 0
    private var _team2Score: Int = 0

    private var _gameDuration: Long = 0

    fun saveWinner(winner: Winner) {
        _winners.add(winner)
    }

    fun clearWinnersList() {
        _winners.clear()
    }

    fun resetGame() {
        _team1Name = ""
        _team2Name = ""
        _team1Score = 0
        _team2Score = 0
        _gameDuration = 0
    }

    fun saveScores(t1: Int, t2: Int) {
        _team1Score = t1
        _team2Score = t2
    }

    fun saveNames(t1: String?, t2: String?) {
        _team1Name = t1 ?: "Placeholder Name"
        _team2Name = t2 ?: "Placeholder Name"
    }

    fun saveDuration(millis: Long) {
        _gameDuration = millis
    }


}