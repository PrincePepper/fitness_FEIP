package com.example.fitness_feip.ui.registration

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.example.fitness_feip.BaseFragment
import com.example.fitness_feip.R
import com.example.fitness_feip.databinding.RegistrationFragmentBinding


class RegistrationFragment :
    BaseFragment<RegistrationFragmentBinding>(R.layout.registration_fragment) {
    var sentence =
        "Нажимая на кнопку, вы соглашаетесь с политикой конфиденциальности и обработки персональных данных, а также принимаете пользовательское соглашение"
    var sentenceClickableList =
        arrayListOf("политикой конфиденциальности", "пользовательское соглашение")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val agreementView = view.findViewById(R.id.agreementView) as TextView
        agreementView.setMovementMethod(LinkMovementMethod.getInstance());
        agreementView.setText(
            addClickablePart(sentence, sentenceClickableList),
            TextView.BufferType.SPANNABLE
        );

        view.findViewById<Toolbar>(R.id.toolbar).setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        view.findViewById<Button>(R.id.btnRegistration).setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_mainFragment)
        }
    }
}