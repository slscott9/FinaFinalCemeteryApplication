package com.example.finalcemeteryproject.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalcemeteryproject.data.Cemetery
import com.example.finalcemeteryproject.databinding.CemeteryListItemBinding

class CemeteryListAdapter(val clickListner: CemeteryListener): ListAdapter<Cemetery, CemeteryListAdapter.ViewHolder>(CemeteryDiffUtilCallback()) {


    class ViewHolder (val binding: CemeteryListItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(
            item: Cemetery,
            clickListener: CemeteryListener
        ){
            binding.cemetery = item
            binding.clickListener = clickListener //14.
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CemeteryListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListner)
    }
}



class CemeteryListener(val clickListener: (id: Int) -> Unit){
    fun onClick(cemetery: Cemetery){
        clickListener(cemetery.cemeteryRowId)
    }
}

class CemeteryDiffUtilCallback: DiffUtil.ItemCallback<Cemetery>() {
    override fun areItemsTheSame(oldItem: Cemetery, newItem: Cemetery): Boolean {
        return oldItem.cemeteryRowId == newItem.cemeteryRowId
    }

    override fun areContentsTheSame(oldItem: Cemetery, newItem: Cemetery): Boolean {
        return oldItem == newItem
    }
}
