package com.rsschool.android2021

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class DialogAlert : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Оповещение")
                .setMessage("Закрать приложение?")
                .setCancelable(true)
                .setPositiveButton("Да") { dialog, id -> System.exit(0) }
                .setNegativeButton("Нет", null)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}