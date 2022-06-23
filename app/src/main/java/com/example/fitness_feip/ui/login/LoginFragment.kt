package com.example.fitness_feip.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fitness_feip.R
import com.example.fitness_feip.api.TokenSP
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class LoginFragment : Fragment(R.layout.login_fragment) {


    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory { LoginViewModel(TokenSP(requireContext())) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Toolbar>(R.id.toolbar).setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.showLoginError.observe(viewLifecycleOwner) {
            when (it) {
                1 -> {
                    view.findViewById<TextInputLayout>(R.id.LoginInput).error =
                        "Введите логин"
                }
                2 -> {
                    view.findViewById<TextInputLayout>(R.id.LoginInput).error =
                        "Пожалуйста напишите почту верно"
                }
                else -> view.findViewById<TextInputLayout>(R.id.LoginInput).error = null
            }
        }

        viewModel.showPasswordError.observe(viewLifecycleOwner) {
            when (it) {
                1 -> {
                    view.findViewById<TextInputLayout>(R.id.PasswordInput).error =
                        "Введите пароль"
                }
                2 -> {
                    view.findViewById<TextInputLayout>(R.id.PasswordInput).error =
                        "Пароль слишком легкий"
                }
                else -> view.findViewById<TextInputLayout>(R.id.PasswordInput).error = null
            }

        }


        view.findViewById<Button>(R.id.btnLogin).setOnClickListener {
            viewModel.onLoginClicked(
                view.findViewById<TextInputEditText>(R.id.login).text.toString(),
                view.findViewById<TextInputEditText>(R.id.password).text.toString()
            )
        }

        viewModel.error.observe(viewLifecycleOwner) {
            if (it == "Okay") {
                findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
            } else {
                if (it.isNotEmpty()) {
                    Toast.makeText(
                        requireActivity().application,
                        it,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}


class ViewModelFactory(private val initializer: () -> ViewModel) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = initializer.invoke() as T
}