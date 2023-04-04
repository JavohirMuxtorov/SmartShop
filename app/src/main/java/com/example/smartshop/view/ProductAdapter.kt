package com.example.smartshop.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smartshop.R
import com.example.smartshop.databinding.ProductItemLayoutBinding
import com.example.smartshop.fragments.ProductDetailFragment
import com.example.smartshop.model.ProductModel
import com.example.smartshop.utils.Constants

class ProductAdapter(val items: List<ProductModel>, val onItemClickListener: OnItemClickListener): RecyclerView.Adapter<ProductAdapter.ItemHolder>() {
    inner class ItemHolder(val binding: ProductItemLayoutBinding): RecyclerView.ViewHolder(binding.root), View.OnClickListener{
        fun bind(item: ProductModel){
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(item)
            }
        }
        override fun onClick(v: View?) {
            onItemClickListener.onItemClick(items[adapterPosition])
        }

    }
    interface OnItemClickListener {
        fun onItemClick(item: ProductModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(ProductItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.binding.tvName.text = item.name
        holder.binding.tvPrice.text = item.price
        Glide.with(holder.itemView.context).load(Constants.HOST_IMAGE+item.image).into(holder.binding.imgProduct)

    }

    override fun getItemCount(): Int {
        return items.count()
    }
}