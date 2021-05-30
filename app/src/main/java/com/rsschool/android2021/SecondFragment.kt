package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

class SecondFragment : Fragment() {

    private var backButton: Button? = null
    private var result: TextView? = null
    private var open_first:IFirstOpen? = null

    private var callback = object: OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            val result = result?.text.toString().toInt()
            open_first?.openFirstView(result)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        open_first = context as IFirstOpen;
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        result = view.findViewById(R.id.result)
        backButton = view.findViewById(R.id.back)

        val min = arguments?.getInt(MIN_VALUE_KEY) ?: 0
        val max = arguments?.getInt(MAX_VALUE_KEY) ?: 0

        result?.text = generate(min, max).toString()

        backButton?.setOnClickListener {
            try {
                val result = result?.text.toString().toInt();
                open_first?.openFirstView(result)
            }catch(e: NumberFormatException){
                val text = "Введите коректные данные"
                val duration = Toast.LENGTH_LONG
                val activity = getActivity()
                Toast.makeText(activity, text, duration).show();
            }
        }
    }

    private fun generate(min: Int, max: Int): Int {
        return (min..max).random();
    }

    override fun onStart() {
        requireActivity().onBackPressedDispatcher.addCallback(callback)
        super.onStart()
    }

    override fun onStop() {
        callback.remove()
        super.onStop()
    }

    companion object {

        @JvmStatic
        fun newInstance(min: Int, max: Int): SecondFragment {
            val fragment = SecondFragment()
            val args = Bundle()
            args.putInt(MIN_VALUE_KEY, min);
            args.putInt(MAX_VALUE_KEY, max)
            fragment.arguments = args;
            return fragment
        }

        private const val MIN_VALUE_KEY = "MIN_VALUE"
        private const val MAX_VALUE_KEY = "MAX_VALUE"
    }

}