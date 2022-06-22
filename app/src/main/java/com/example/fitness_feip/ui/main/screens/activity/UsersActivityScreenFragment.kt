package com.example.fitness_feip.ui.main.screens.activity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitness_feip.CardType
import com.example.fitness_feip.CardsData
import com.example.fitness_feip.R
import com.example.fitness_feip.adapter.RecyclerViewAdapter


class UsersActivityScreenFragment : Fragment(R.layout.fragment_users_activity_screen) {

    private val activityData = CardsData()

    private val adapter = RecyclerViewAdapter {
        findNavController().navigate(

            ActivityScreenDirections.actionActivityScreenToWorkoutDetailsScreen(
                it.distance,
                it.period,
                it.typeActivity,
                it.dateActivity,
                it.cardType,
                it.nickname
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<RecyclerView>(R.id.UsersCards).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@UsersActivityScreenFragment.adapter
        }
        adapter.setData(activityData.getDefaultData(CardType.users))
    }
}