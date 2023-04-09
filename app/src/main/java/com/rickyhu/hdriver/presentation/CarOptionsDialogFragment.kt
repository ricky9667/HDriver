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
    private val options =
        listOf(
            CarOption(if (item.isFavorite) "取消最愛" else "加入最愛") {
                viewModel.toggleFavorite(item)
            },
            CarOption("複製連結") {
                copyToClipboard()
            },
            CarOption("刪除車號") {
                deleteItem(item)
            }
        )

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val optionTitles = options.map { it.title }.toTypedArray()
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("選項")
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
        val dialog = AlertDialog.Builder(requireContext()).setTitle("刪除車號")
            .setMessage("你確定要刪除 ${item.number} 嗎？")
            .setPositiveButton(getString(R.string.dialog_ok)) { _, _ ->
                viewModel.deleteCarItem(item)
            }.setNegativeButton(getString(R.string.dialog_cancel)) { _, _ -> }.create()
        dialog.show()
    }
}
