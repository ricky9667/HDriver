package com.rickyhu.hdriver.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rickyhu.hdriver.data.RecentListItem
import com.rickyhu.hdriver.databinding.FragmentRecentItemBinding

class RecentListAdapter :
    ListAdapter<RecentListItem, RecentListAdapter.ViewHolder>(DiffCallback) {

    private var listener: RecentItemClickListener? = null

    fun setOnItemClickListener(listener: RecentItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(binding: FragmentRecentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val godNumberView = binding.godNumber
        val godUrlView = binding.godUrl

        init {
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    listener?.onClick(item)
                }
            }
        }

        override fun toString(): String = godUrlView.text.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentRecentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
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

    interface RecentItemClickListener {
        fun onClick(item: RecentListItem)
    }
}
