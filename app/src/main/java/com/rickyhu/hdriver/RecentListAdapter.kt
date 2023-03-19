package com.rickyhu.hdriver

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rickyhu.hdriver.databinding.FragmentRecentItemBinding

class RecentListAdapter :
    ListAdapter<RecentListItem, RecentListAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(binding: FragmentRecentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val godNumberView: TextView = binding.godNumber
        val godUrlView = binding.godUrl

        override fun toString(): String = godUrlView.text.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentRecentItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.godNumberView.text = item.number
        holder.godUrlView.text = "https://nhentai.net/g/${item.number}"
    }

    companion object DiffCallback : DiffUtil.ItemCallback<RecentListItem>() {
        override fun areItemsTheSame(oldItem: RecentListItem, newItem: RecentListItem) =
            oldItem.number == newItem.number

        override fun areContentsTheSame(oldItem: RecentListItem, newItem: RecentListItem) =
            oldItem == newItem

    }
}