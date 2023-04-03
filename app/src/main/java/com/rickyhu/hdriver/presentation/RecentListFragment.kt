package com.rickyhu.hdriver.presentation

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
import com.rickyhu.hdriver.data.model.GodItem
import com.rickyhu.hdriver.databinding.FragmentRecentItemListBinding
import com.rickyhu.hdriver.viewmodel.RecentListViewModel
import com.rickyhu.hdriver.viewmodel.RecentListViewModelFactory

class RecentListFragment : Fragment() {

    private var _binding: FragmentRecentItemListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecentListViewModel by activityViewModels {
        RecentListViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecentItemListBinding.inflate(inflater, container, false)

        // setup recent list item click listeners
        val adapter = RecentListAdapter()
        adapter.apply {
            setOnItemClickListener(object : RecentListAdapter.RecentItemClickListener {
                override fun onClick(item: GodItem) = openWebView(item.url)
            })
            setOnLongClickListener(object : RecentListAdapter.RecentItemClickListener {
                override fun onClick(item: GodItem) = copyUrlToClipboard(item.url)
            })
            setOnDeleteClickListener(object : RecentListAdapter.RecentItemClickListener {
                override fun onClick(item: GodItem) = viewModel.deleteRecentItem(item)
            })
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

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
        val clipboard = requireActivity()
            .getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("url", url)
        clipboard.setPrimaryClip(clip)
    }

    companion object {
        // TODO: extract url
        const val baseUrl = "https://nhentai.net/g/{query}"
    }
}
