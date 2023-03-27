package com.example.smartshop.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smartshop.activity.ProductDetailActivity
import com.example.smartshop.databinding.TopProductItemLayoutBinding
import com.example.smartshop.model.TopProductModel
import com.example.smartshop.utils.Constants


class TopProductAdapter(val items: List<TopProductModel>): RecyclerView.Adapter<TopProductAdapter.ItemHolder>() {
    inner class ItemHolder(val binding: TopProductItemLayoutBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(TopProductItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProductDetailActivity::class.java)
//            intent.("topProduct",item)
            intent.putExtra("topProduct",item)
            holder.itemView.context.startActivity(intent)
        }
        holder.binding.tvName.text = item.name
        holder.binding.tvPrice.text = item.price
        Glide.with(holder.itemView.context).load(Constants.HOST_IMAGE+item.image).into(holder.binding.imgProduct)

    }

    override fun getItemCount(): Int {
        return items.count()
    }
}