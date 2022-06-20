package com.example.fitness_feip

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

open class BaseFragment<V : ViewBinding>(@LayoutRes layoutResId: Int) : Fragment(layoutResId) {

    protected val binding: V
        get() = _binding!!

    private var _binding: V? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
            ?.also { _binding = provideViewBinding(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @Suppress("UNCHECKED_CAST")
    protected open fun provideViewBinding(view: View): V {
        val bindingType = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        val bindingClass = bindingType as Class<V>
        val method = bindingClass.getMethod("bind", View::class.java)
        return method.invoke(null, view) as V
    }

    protected fun addClickablePart(str: String, list: List<String>): SpannableStringBuilder {
        val ssb = SpannableStringBuilder(str)
        val listSpanneble: ArrayList<Int> = ArrayList<Int>()
        for (i in list) {
            listSpanneble.add(str.indexOf(i))
            listSpanneble.add(i.length + str.indexOf(i))
        }
        while (listSpanneble.size != 0) {

            val clickString = str.substring(listSpanneble[0], listSpanneble[1])
            ssb.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    //TODO что необходимо c clickString

                    Toast.makeText(
                        requireActivity().application,
                        clickString,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }, listSpanneble[0], listSpanneble[1], 0)
            listSpanneble.removeAt(0)
            listSpanneble.removeAt(0)
        }
        return ssb
    }


}