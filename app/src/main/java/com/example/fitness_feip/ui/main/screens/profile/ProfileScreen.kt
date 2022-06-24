package com.example.fitness_feip.ui.main.screens.profile

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.fitness_feip.R
import com.example.fitness_feip.api.TokenSP
import com.example.fitness_feip.ui.main.MainFragment
import com.google.android.material.textfield.TextInputEditText

class ProfileScreen : Fragment(R.layout.main_profile_screen_fragment) {

    private val viewModel by viewModels<ProfileScreenViewModel> {
        ViewModelFactory { ProfileScreenViewModel(TokenSP(requireContext())) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.login.observe(viewLifecycleOwner) {
            view.findViewById<TextInputEditText>(R.id.login).setText(it)
        }

        viewModel.name.observe(viewLifecycleOwner) {
            view.findViewById<TextInputEditText>(R.id.name).setText(it)
        }

        view.findViewById<Button>(R.id.button_logout).setOnClickListener {
            viewModel.onLogoutClicked()
        }

        viewModel.error.observe(viewLifecycleOwner) {
            if (it == "Okay") {
                val navHostFragment = parentFragment as NavHostFragment
                val parent: MainFragment? = navHostFragment.parentFragment as MainFragment?
                parent?.findNavController()?.navigate(R.id.action_mainFragment_to_welcomeFragment)
            } else if (it != "") {
                Toast.makeText(
                    requireActivity().application,
                    it,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}


class ViewModelFactory(private val initializer: () -> ViewModel) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = initializer.invoke() as T
}