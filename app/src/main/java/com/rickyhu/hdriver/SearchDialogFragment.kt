package com.rickyhu.hdriver

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.rickyhu.hdriver.databinding.DialogSearchBinding

class SearchDialogFragment : DialogFragment() {
    private lateinit var binding: DialogSearchBinding
    private val NHENTAI_BASE_URL = "https://nhentai.net/g"

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogSearchBinding.inflate(LayoutInflater.from(activity))

        val builder = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setTitle(getString(R.string.text_set_car_information))

        binding.buttonSearch.setOnClickListener {
            val godNumberText = binding.edittextGodNumber.text.toString()
            val godNumber = godNumberText.toIntOrNull()

            if (godNumber != null) {
                val url = "$NHENTAI_BASE_URL/$godNumber"
                val intent = Intent(activity, WebViewActivity::class.java)
                intent.putExtra("url", url)
                startActivity(intent)
            } else {
                Log.e("SearchDialogFragment", "unable to load view")
            }
        }

        return builder.create()
    }

    private fun openWebView(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}