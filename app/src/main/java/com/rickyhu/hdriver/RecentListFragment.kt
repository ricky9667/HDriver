package com.rickyhu.hdriver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rickyhu.hdriver.databinding.FragmentRecentItemListBinding
import com.rickyhu.hdriver.placeholder.PlaceholderContent

class RecentListFragment : Fragment() {

    private var _binding: FragmentRecentItemListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecentItemListBinding.inflate(inflater, container, false)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = RecentListAdapter(PlaceholderContent.ITEMS)
        }
        return binding.root
    }
}