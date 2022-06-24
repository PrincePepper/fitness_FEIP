package com.example.fitness_feip.ui.registration

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fitness_feip.BaseFragment
import com.example.fitness_feip.R
import com.example.fitness_feip.api.TokenSP
import com.example.fitness_feip.databinding.RegistrationFragmentBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class RegistrationFragment :
    BaseFragment<RegistrationFragmentBinding>(R.layout.registration_fragment) {
    var sentence =
        "Нажимая на кнопку, вы соглашаетесь с политикой конфиденциальности и обработки персональных данных, а также принимаете пользовательское соглашение"
    var sentenceClickableList =
        arrayListOf("политикой конфиденциальности", "пользовательское соглашение")

    private val viewModel by viewModels<RegistrationViewModel> {
        ViewModelFactory { RegistrationViewModel(TokenSP(requireContext())) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val agreementView = view.findViewById(R.id.agreementView) as TextView
        agreementView.movementMethod = LinkMovementMethod.getInstance();
        agreementView.setText(
            addClickablePart(sentence, sentenceClickableList),
            TextView.BufferType.SPANNABLE
        );

        view.findViewById<Toolbar>(R.id.toolbar).setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.showLoginError.observe(viewLifecycleOwner) {
            when (it) {
                1 -> {
                    view.findViewById<TextInputLayout>(R.id.LoginInput).error = "Введите логин"
                }
                2 -> {
                    view.findViewById<TextInputLayout>(R.id.LoginInput).error =
                        "Пожалуйста напишите почту верно"
                }
                else -> view.findViewById<TextInputLayout>(R.id.LoginInput).error = null
            }
        }

        viewModel.showNameError.observe(viewLifecycleOwner) {
            if (it) {
                view.findViewById<TextInputLayout>(R.id.NameInput).error =
                    "Введите имя или никнейм"
            } else {
                view.findViewById<TextInputLayout>(R.id.NameInput).error = null
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

        viewModel.showWrongRepeatPasswordError.observe(viewLifecycleOwner) {
            if (it) {
                view.findViewById<TextInputLayout>(R.id.RepeatPasswordInput).error =
                    "Неверный повтор пароля"
            } else {
                view.findViewById<TextInputLayout>(R.id.RepeatPasswordInput).error = null
            }
        }

        var gender = 0
        view.findViewById<RadioGroup>(R.id.radioGroup_reg)
            .setOnCheckedChangeListener { _, checkedId ->
                if (checkedId == R.id.radioVibrateRun) {
                    gender = 0
                } else if (checkedId == R.id.radioVibrateStop) {
                    gender = 1
                }
            }

        view.findViewById<Button>(R.id.btnRegistration).setOnClickListener {
            viewModel.onRegistrationClicked(
                view.findViewById<TextInputEditText>(R.id.login).text.toString(),
                view.findViewById<TextInputEditText>(R.id.name).text.toString(),
                view.findViewById<TextInputEditText>(R.id.password).text.toString(),
                view.findViewById<TextInputEditText>(R.id.repeatpassword).text.toString(),
                gender
            )
        }

        viewModel.error.observe(viewLifecycleOwner) {
            if (it == "Okay") {
                findNavController().navigate(R.id.action_registrationFragment_to_mainFragment)
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