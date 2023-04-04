package com.example.smartshop.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smartshop.databinding.CartItemLayoutBinding
import com.example.smartshop.model.TopProductModel
import com.example.smartshop.utils.Constants

class CartAdapter( val items:List<TopProductModel>):RecyclerView.Adapter<CartAdapter.ItemHolder>(){
    inner class ItemHolder(val binding: CartItemLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(CartItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))    }

    override fun getItemCount(): Int {
        return items.count()
    }
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]

        holder.binding.tvPrice.text = item.price
        holder.binding.tvName.text = item.name
        Glide.with(holder.itemView).load(Constants.HOST_IMAGE + item.image).into(holder.binding.imgProduct)
        var count = item.cartCount+1
        holder.binding.tvCount.text = count.toString()
        item.cartCount = count
        holder.binding.increment.setOnClickListener {
            count++
            item.cartCount = count
            holder.binding.tvCount.text = count.toString()
        }
        holder.binding.decrement.setOnClickListener {
            if (count >1){
                count--
                item.cartCount = count
                holder.binding.tvCount.text = count.toString()
            }
        }
    }

}
