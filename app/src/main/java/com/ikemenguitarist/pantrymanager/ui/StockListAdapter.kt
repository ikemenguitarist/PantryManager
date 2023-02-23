package com.ikemenguitarist.pantrymanager.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ikemenguitarist.pantrymanager.data.Item
import com.ikemenguitarist.pantrymanager.data.Stocker
import com.ikemenguitarist.pantrymanager.databinding.FragmentItemListItemBinding
import com.ikemenguitarist.pantrymanager.databinding.FragmentStockListItemBinding

class StockListAdapter(private val onStockClicked: (Stocker) -> Unit) :
    ListAdapter<Stocker, StockListAdapter.StockViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        return StockViewHolder(
            FragmentStockListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onStockClicked(current)
        }
        holder.bind(current)
    }

    class StockViewHolder(private var binding: FragmentStockListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(stocker: Stocker) {
            binding.itemName.text = stocker.name
            binding.itemCount.text = stocker.description

        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Stocker>() {
            override fun areItemsTheSame(oldItem: Stocker, newItem: Stocker): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Stocker, newItem: Stocker): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}