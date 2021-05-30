package com.rsschool.android2021

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var min: EditText? = null
    private var max: EditText? = null
    private var second_open: ISecondOpen? = null

    private var callback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            val dialog = DialogAlert()
            val manager = childFragmentManager
            dialog.show(manager, "Exit")
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        second_open = context as ISecondOpen;
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        min = view.findViewById(R.id.min_value);
        max = view.findViewById(R.id.max_value);

        generateButton?.setOnClickListener {
            try {
                val min_number = min?.text.toString().toInt();
                val max_number = max?.text.toString().toInt();
                if(min_number > max_number)
                    toast_open("Минимальное больше максимального");
                else
                    second_open?.openSecondView(min_number,max_number)
            }catch(e: NumberFormatException){
                toast_open();
            }
        }
    }

    fun toast_open(text: String = "Введите коректные данные"){
        val duration = Toast.LENGTH_LONG
        val activity = getActivity()
        Toast.makeText(activity, text, duration).show();
    }

    override fun onStart() {
        super.onStart()
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    override fun onStop() {
        callback.remove()
        super.onStop()
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}