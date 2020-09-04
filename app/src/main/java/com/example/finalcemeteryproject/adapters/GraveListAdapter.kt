package com.example.finalcemeteryproject.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalcemeteryproject.data.Grave
import com.example.finalcemeteryproject.databinding.GraveListItemBinding

class GraveListAdapter(val clickListener: GraveListListener) : ListAdapter<Grave, GraveListAdapter.ViewHolder>(GraveDiffUtilCallback()) {

    class ViewHolder(val binding: GraveListItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Grave, listener: GraveListListener){
            binding.grave = item
            binding.clickListener = listener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = GraveListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)

    }
}

class GraveDiffUtilCallback: DiffUtil.ItemCallback<Grave>(){
    override fun areItemsTheSame(oldItem: Grave, newItem: Grave): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Grave, newItem: Grave): Boolean {
        return oldItem == newItem
    }
}
class GraveListListener(val clickListener: (id: Int) -> Unit) {
    fun onClick(grave: Grave) {
        clickListener(grave.id)
    }
}