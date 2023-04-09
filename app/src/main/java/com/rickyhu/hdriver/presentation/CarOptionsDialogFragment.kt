package com.rickyhu.hdriver.presentation

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import com.rickyhu.hdriver.data.model.CarItem

data class CarOption(val title: String, val onClick: () -> Unit)

class CarOptionsDialogFragment(val item: CarItem) : DialogFragment() {
    private val options =
        listOf(
            CarOption(if (item.isFavorite) "取消收藏" else "加入收藏") {
                Log.d("CarOptionsDialogFragment", "加入收藏")
            },
            CarOption("複製連結") {
                Log.d("CarOptionsDialogFragment", "複製連結")
            },
            CarOption("刪除") {
                Log.d("CarOptionsDialogFragment", "刪除")
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
}
