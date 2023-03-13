package com.rickyhu.hdriver

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.rickyhu.hdriver.databinding.DialogSearchBinding

class SearchDialogFragment : DialogFragment() {
    private lateinit var binding: DialogSearchBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogSearchBinding.inflate(LayoutInflater.from(activity))

        val builder = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setTitle(getString(R.string.text_set_car_information))

        binding.buttonSearch.setOnClickListener {
            Log.d("SearchDialogFragment", "Search button clicked")
        }

        return builder.create()
    }
}