package com.rickyhu.hdriver.presentation

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rickyhu.hdriver.R
import com.rickyhu.hdriver.data.model.CarItem
import com.rickyhu.hdriver.databinding.FragmentCarListBinding
import com.rickyhu.hdriver.viewmodel.CarListViewModel
import com.rickyhu.hdriver.viewmodel.CarListViewModelFactory

class CarListFragment : Fragment() {

    private var _binding: FragmentCarListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CarListViewModel by activityViewModels {
        CarListViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCarListBinding.inflate(inflater, container, false)

        // setup recent list item click listeners
        val adapter = CarListAdapter()
        adapter.apply {
            setOnItemClickListener(object : CarListAdapter.ItemClickListener {
                override fun onClick(item: CarItem) = viewCarListItem(item)
            })
            setOnLongClickListener(object : CarListAdapter.ItemClickListener {
                override fun onClick(item: CarItem) = showCarOptionsDialog(item)
            })
            setOnDeleteClickListener(object : CarListAdapter.ItemClickListener {
                override fun onClick(item: CarItem) = deleteItem(item)
            })
        }

        binding.recyclerViewCarList.adapter = adapter
        binding.recyclerViewCarList.layoutManager = LinearLayoutManager(context)

        // observe recent list changes from view model
        viewModel.onRecentListChanged = { adapter.submitList(it) }

        return binding.root
    }

    private fun viewCarListItem(item: CarItem) {
        viewModel.updateCarItemViewTime(item)
        openWebView(item.url)
    }

    private fun openWebView(url: String) {
        val intent = Intent(activity, WebViewActivity::class.java)
        intent.putExtra("url", url)
        startActivity(intent)
    }

    private fun showCarOptionsDialog(item: CarItem) {
        val dialog = CarOptionsDialogFragment(item)
        dialog.show(parentFragmentManager, "CarOptionsDialogFragment")
    }

    private fun copyUrlToClipboard(url: String) {
        val clipboard =
            requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("url", url)
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
