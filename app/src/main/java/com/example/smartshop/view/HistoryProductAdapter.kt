package com.example.smartshop.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smartshop.activity.HistoryProductDetailActivity
import com.example.smartshop.activity.ProductDetailActivity
import com.example.smartshop.databinding.HistoryProductItemLayoutBinding
import com.example.smartshop.model.ProductDetailModel
import com.example.smartshop.model.SearchModel
import com.example.smartshop.utils.Constants

class HistoryProductAdapter(var items: List<SearchModel>): RecyclerView.Adapter<HistoryProductAdapter.ItemHolder>() {
    inner class ItemHolder(val binding: HistoryProductItemLayoutBinding): RecyclerView.ViewHolder(binding.root)
    fun setFilteredList(searchProduct: List<SearchModel>){
        items = searchProduct
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(HistoryProductItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,HistoryProductDetailActivity::class.java)
            intent.putExtra("item",item)
            holder.itemView.context.startActivity(intent)
        }
        holder.binding.title.text = item.name
        holder.binding.price.text = item.price
        Glide.with(holder.itemView.context).load(Constants.HOST_IMAGE+item.image).into(holder.binding.imgProduct)

    }

    override fun getItemCount(): Int {
        return items.count()
    }
}