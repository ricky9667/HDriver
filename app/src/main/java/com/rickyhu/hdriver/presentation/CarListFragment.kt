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
import com.rickyhu.hdriver.viewmodel.RecentListViewModel
import com.rickyhu.hdriver.viewmodel.RecentListViewModelFactory

class CarListFragment : Fragment() {

    private var _binding: FragmentCarListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecentListViewModel by activityViewModels {
        RecentListViewModelFactory(requireContext())
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
            setOnItemClickListener(object : CarListAdapter.RecentItemClickListener {
                override fun onClick(item: CarItem) = openWebView(item.url)
            })
            setOnLongClickListener(object : CarListAdapter.RecentItemClickListener {
                override fun onClick(item: CarItem) = copyUrlToClipboard(item.url)
            })
            setOnDeleteClickListener(object : CarListAdapter.RecentItemClickListener {
                override fun onClick(item: CarItem) = deleteItem(item)
            })
        }

        binding.recyclerViewCarList.adapter = adapter
        binding.recyclerViewCarList.layoutManager = LinearLayoutManager(context)

        // observe recent list changes from view model
        viewModel.onRecentListChanged = { adapter.submitList(it) }

        return binding.root
    }

    private fun openWebView(url: String) {
        val intent = Intent(activity, WebViewActivity::class.java)
        intent.putExtra("url", url)
        startActivity(intent)
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
                viewModel.deleteRecentItem(item)
            }.setNegativeButton(getString(R.string.dialog_cancel)) { _, _ -> }.create()
        dialog.show()
    }
}
