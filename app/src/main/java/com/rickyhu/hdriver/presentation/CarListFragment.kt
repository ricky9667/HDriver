package com.rickyhu.hdriver.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
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
        }

        binding.recyclerViewCarList.adapter = adapter
        binding.recyclerViewCarList.layoutManager = LinearLayoutManager(context)

        // observe recent list changes from view model
        viewModel.onRecentListChanged = { adapter.submitList(it) }

        return binding.root
    }

    private fun viewCarListItem(item: CarItem) {
        viewModel.updateCarItemViewTime(item)
        val intent = Intent(activity, WebViewActivity::class.java)
        intent.putExtra("url", item.url)
        startActivity(intent)
    }

    private fun showCarOptionsDialog(item: CarItem) {
        val dialog = CarOptionsDialogFragment(viewModel, item)
        dialog.show(parentFragmentManager, "CarOptionsDialogFragment")
    }
}
