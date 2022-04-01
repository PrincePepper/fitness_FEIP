package com.example.fitness_feip.ui.welcome

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fitness_feip.R
import com.example.fitness_feip.databinding.ActivityMainBinding

class WelcomeFragment : Fragment(R.layout.welcome_fragment) {

    private lateinit var binding: ActivityMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding= ActivityMainBinding.inflate(layoutInflater)

        view.findViewById<Button>(R.id.button_reg).setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_registrationFragment)
        }

        view.findViewById<TextView>(R.id.textView_btn_login).setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment)
        }
    }
}