package com.example.fitness_feip.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fitness_feip.R
import com.example.fitness_feip.api.TokenSP


class SplashFragment : Fragment(R.layout.fragment_splash) {
    val Runnable1 = Runnable {
        findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
    }
    val Runnable2 = Runnable {
        findNavController().navigate(R.id.action_splashFragment_to_welcomeFragment)
    }
    private var delay: Int = 1500
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (TokenSP(context).checkToken()) {
            view.postDelayed(Runnable1, delay.toLong())
        } else {
            view.postDelayed(Runnable2, delay.toLong())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        view?.removeCallbacks { Runnable1 }
        view?.removeCallbacks(Runnable2)
    }
}