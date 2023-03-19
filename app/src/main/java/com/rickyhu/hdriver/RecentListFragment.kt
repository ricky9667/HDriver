package com.rickyhu.hdriver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rickyhu.hdriver.databinding.FragmentRecentItemListBinding

class RecentListFragment : Fragment() {

    private var _binding: FragmentRecentItemListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecentListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecentItemListBinding.inflate(inflater, container, false)

        val adapter = RecentListAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.godNumberList.observe(viewLifecycleOwner) { adapter.submitList(it) }

        return binding.root
    }
}