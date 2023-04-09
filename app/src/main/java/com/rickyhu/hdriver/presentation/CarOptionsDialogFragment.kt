package com.rickyhu.hdriver.presentation

import android.app.AlertDialog
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.rickyhu.hdriver.R
import com.rickyhu.hdriver.data.model.CarItem
import com.rickyhu.hdriver.viewmodel.CarListViewModel

data class CarOption(val title: String, val onClick: () -> Unit)

class CarOptionsDialogFragment(private val viewModel: CarListViewModel, val item: CarItem) :
    DialogFragment() {
    private lateinit var options: List<CarOption>

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        options = listOf(
            if (item.isFavorite) {
                CarOption(getString(R.string.text_remove_from_favorites)) {
                    viewModel.toggleFavorite(item)
                }
            } else {
                CarOption(getString(R.string.text_add_to_favorites)) {
                    viewModel.toggleFavorite(item)
                }
            },
            CarOption(getString(R.string.text_copy_link)) {
                copyToClipboard()
            },
            CarOption(getString(R.string.text_delete_car_item)) {
                deleteItem(item)
            }
        )

        val optionTitles = options.map { it.title }.toTypedArray()
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(getString(R.string.text_options))
                .setItems(optionTitles) { dialog, index ->
                    options[index].onClick()
                    dialog.dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun copyToClipboard() {
        val clipboard = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("url", item.url)
        clipboard.setPrimaryClip(clip)
    }

    private fun deleteItem(item: CarItem) {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.text_delete_car_item))
            .setMessage("你確定要刪除 ${item.number} 嗎？")
            .setPositiveButton(getString(R.string.dialog_ok)) { _, _ ->
                viewModel.deleteCarItem(item)
            }.setNegativeButton(getString(R.string.dialog_cancel)) { _, _ -> }.create()
        dialog.show()
    }
}
