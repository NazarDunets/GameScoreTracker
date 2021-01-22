package com.example.gamescoretracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController

        setSupportActionBar(findViewById(R.id.abCustom))

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.newGameFragment,
                R.id.currentGameFragment,
                R.id.resultsFragment,
                R.id.leaderboardFragment
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}