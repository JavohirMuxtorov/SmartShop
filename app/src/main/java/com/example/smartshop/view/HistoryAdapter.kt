package com.example.smartshop.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartshop.databinding.CategoryItemLayoutBinding
import com.example.smartshop.databinding.HistoryItemLayoutBinding
import com.example.smartshop.model.HistoryModel

class HistoryAdapter(var items: List<HistoryModel>, val onItemClickListener: OnItemClickListener): RecyclerView.Adapter<HistoryAdapter.ItemHolder>() {
    inner class ItemHolder(val binding: HistoryItemLayoutBinding): RecyclerView.ViewHolder(binding.root), View.OnClickListener{
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            onItemClickListener.onItemClick(adapterPosition)
        }

    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)


}
    fun setFilteredList(mHistoryList: List<HistoryModel>){
        this.items = mHistoryList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(HistoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]

        holder.binding.tvHistory.text = item.text
    }

    override fun getItemCount(): Int {
        return items.count()
    }

}