package com.example.fitness_feip.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.fitness_feip.R
import com.example.fitness_feip.databinding.ActivityMainBinding

class MainFragment : Fragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
        const val usernamekey = "USER_NAME"
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}