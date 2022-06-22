package com.example.fitness_feip.ui.main.screens.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitness_feip.CardType
import com.example.fitness_feip.CardsData
import com.example.fitness_feip.R
import com.example.fitness_feip.adapter.RecyclerViewAdapter


class MyActivityScreenFragment : Fragment(R.layout.fragment_my_activity_screen) {

    private val activityData = CardsData()

    private val adapter = RecyclerViewAdapter {
        findNavController().navigate(
            ActivityScreenDirections.actionActivityScreenToWorkoutDetailsScreen(
                it.distance,
                it.period,
                it.typeActivity,
                it.dateActivity,
                it.cardType,
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<RecyclerView>(R.id.MyCards).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@MyActivityScreenFragment.adapter
        }
        if (activityData.getDefaultData(CardType.my).isNotEmpty()) {
            adapter.setData(activityData.getDefaultData(CardType.my))
            view.findViewById<TextView>(R.id.text_one).visibility = View.GONE
            view.findViewById<TextView>(R.id.text_two).visibility = View.GONE
        }
    }

}