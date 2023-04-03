package com.rickyhu.hdriver.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rickyhu.hdriver.data.model.CarItem
import com.rickyhu.hdriver.databinding.FragmentCarItemBinding

class CarListAdapter : ListAdapter<CarItem, CarListAdapter.ViewHolder>(DiffCallback) {

    private var listener: RecentItemClickListener? = null
    private var longClickListener: RecentItemClickListener? = null
    private var deleteListener: RecentItemClickListener? = null

    fun setOnItemClickListener(listener: RecentItemClickListener) {
        this.listener = listener
    }

    fun setOnLongClickListener(listener: RecentItemClickListener) {
        this.longClickListener = listener
    }

    fun setOnDeleteClickListener(listener: RecentItemClickListener) {
        this.deleteListener = listener
    }

    inner class ViewHolder(binding: FragmentCarItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val godNumberView = binding.godNumber
        val godUrlView = binding.godUrl
        private val deleteButton = binding.buttonDeleteItem

        init {
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    listener?.onClick(item)
                }
            }

            itemView.setOnLongClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    longClickListener?.onClick(item)
                }
                true
            }

            deleteButton.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    deleteListener?.onClick(item)
                }
            }
        }

        override fun toString(): String = godUrlView.text.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentCarItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.godNumberView.text = item.number
        holder.godUrlView.text = item.url
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CarItem>() {
        override fun areItemsTheSame(oldItem: CarItem, newItem: CarItem) =
            oldItem.number == newItem.number

        override fun areContentsTheSame(oldItem: CarItem, newItem: CarItem) =
            oldItem == newItem
    }

    interface RecentItemClickListener {
        fun onClick(item: CarItem)
    }
}