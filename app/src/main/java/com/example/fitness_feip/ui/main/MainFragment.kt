package com.example.fitness_feip.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.fitness_feip.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainFragment : Fragment(R.layout.main_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavView = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navController =
            (childFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment).navController

        bottomNavView.setupWithNavController(navController)
    }

}