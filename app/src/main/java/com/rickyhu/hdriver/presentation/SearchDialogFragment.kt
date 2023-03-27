package com.rickyhu.hdriver.presentation

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.rickyhu.hdriver.R
import com.rickyhu.hdriver.databinding.DialogSearchBinding
import com.rickyhu.hdriver.viewmodel.RecentListViewModel
import com.rickyhu.hdriver.viewmodel.RecentListViewModelFactory

class SearchDialogFragment : DialogFragment() {
    private lateinit var binding: DialogSearchBinding

    private val viewModel: RecentListViewModel by activityViewModels {
        RecentListViewModelFactory(requireContext())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogSearchBinding.inflate(LayoutInflater.from(activity))

        val builder = AlertDialog.Builder(requireContext()).setView(binding.root)
            .setTitle(getString(R.string.text_set_car_information))

        binding.buttonSearch.setOnClickListener {
            val godNumberText = binding.edittextGodNumber.text.toString()
            val godNumber = godNumberText.toIntOrNull()

            if (godNumber != null) {
                val url = baseUrl.replace("{query}", godNumberText)
                openWebView(url)
                viewModel.addRecentItem(godNumberText, url)
            } else {
                showOpenWebViewFailToast()
            }
        }

        return builder.create()
    }

    private fun openWebView(url: String) {
        val intent = Intent(activity, WebViewActivity::class.java)
        intent.putExtra("url", url)
        startActivity(intent)
    }

    private fun showOpenWebViewFailToast() {
        val toast = Toast.makeText(activity?.applicationContext, "無法開啟頁面", Toast.LENGTH_SHORT)
        toast.show()
    }

    companion object {
        const val baseUrl = "https://nhentai.net/g/{query}"
    }
}
