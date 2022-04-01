package com.example.fitness_feip.ui.registration

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fitness_feip.R


class RegistrationFragment : Fragment(R.layout.registration_fragment) {
    var sentence =
        "Нажимая на кнопку, вы соглашаетесь с [политикой конфиденциальности] и обработки персональных данных, а также принимаете [пользовательское соглашение]"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val agreementView = view.findViewById(R.id.agreementView) as TextView
        agreementView.setMovementMethod(LinkMovementMethod.getInstance());
        agreementView.setText(addClickablePart(sentence), TextView.BufferType.SPANNABLE);

        view.findViewById<Toolbar>(R.id.toolbar).setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        view.findViewById<Button>(R.id.btnRegistration).setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_mainFragment)
        }
    }

    private fun addClickablePart(str: String): SpannableStringBuilder {
        val ssb = SpannableStringBuilder(str)
        var idx1 = str.indexOf("[")
        var idx2: Int
        while (idx1 != -1) {
            idx2 = str.indexOf("]", idx1) + 1
            val clickString = str.substring(idx1, idx2)
            ssb.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    //TODO что необходимо c clickString

                    Toast.makeText(
                        requireActivity().application,
                        clickString,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }, idx1, idx2, 0)
            //TODO: надо понять как избавиться от "[" и "]"
//            ssb.setSpan(' ', idx1, idx1+1, 0)
            idx1 = str.indexOf("[", idx2)
        }


        return ssb
    }

}