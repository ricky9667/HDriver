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
import com.rickyhu.hdriver.viewmodel.CarListViewModel
import com.rickyhu.hdriver.viewmodel.CarListViewModelFactory

class SearchDialogFragment : DialogFragment() {
    private lateinit var binding: DialogSearchBinding

    private val viewModel: CarListViewModel by activityViewModels {
        CarListViewModelFactory(requireContext())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogSearchBinding.inflate(LayoutInflater.from(activity))
        binding.buttonSearch.setOnClickListener { onSearchButtonClick() }

        val builder = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setTitle(getString(R.string.text_set_car_information))
        return builder.create()
    }

    private fun onSearchButtonClick() {
        val carNumberText = binding.edittextCarNumber.text.toString()
        val carNumber = carNumberText.toIntOrNull()

        if (carNumber != null) {
            val url = baseUrl.replace(getString(R.string.query_string), carNumberText)
            openWebView(url)
            viewModel.viewCarItem(carNumberText, url)
        } else {
            showOpenWebViewFailToast()
        }
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
        // TODO: extract url
        const val baseUrl = "https://nhentai.net/g/{query}"
    }
}
