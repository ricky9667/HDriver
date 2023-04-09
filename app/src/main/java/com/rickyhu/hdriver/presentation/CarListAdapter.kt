package com.rickyhu.hdriver.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rickyhu.hdriver.data.model.CarItem
import com.rickyhu.hdriver.databinding.FragmentCarItemBinding

class CarListAdapter : ListAdapter<CarItem, CarListAdapter.ViewHolder>(DiffCallback) {

    private var listener: ItemClickListener? = null
    private var longClickListener: ItemClickListener? = null

    fun setOnItemClickListener(listener: ItemClickListener) {
        this.listener = listener
    }

    fun setOnLongClickListener(listener: ItemClickListener) {
        this.longClickListener = listener
    }

    inner class ViewHolder(binding: FragmentCarItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val carNumberView = binding.carNumber
        val carUrlView = binding.carUrl

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
        }
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
        holder.carNumberView.text = item.number
        holder.carUrlView.text = item.url
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CarItem>() {
        override fun areItemsTheSame(oldItem: CarItem, newItem: CarItem) =
            oldItem.number == newItem.number

        override fun areContentsTheSame(oldItem: CarItem, newItem: CarItem) =
            oldItem == newItem
    }

    interface ItemClickListener {
        fun onClick(item: CarItem)
    }
}
