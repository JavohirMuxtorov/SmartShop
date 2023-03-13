package com.example.smartshop.view

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smartshop.R
import com.example.smartshop.activity.CategoriesProductActivity
import com.example.smartshop.databinding.CategoryItemLayoutBinding
import com.example.smartshop.model.CategoryModel
import com.example.smartshop.utils.Constants


//interface CategoryAdapterCallback{
//    fun onClick(item: CategoryModel)
//}
class CategoryAdapter(val items: List<CategoryModel>): RecyclerView.Adapter<CategoryAdapter.ItemHolder>() {
    inner class ItemHolder(val binding: CategoryItemLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(CategoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.itemView.setOnClickListener {
//            items.forEach {
//                it.checked = false
//            }
//
//            item.checked = true
            val intent = Intent(holder.itemView.context,CategoriesProductActivity::class.java)
            intent.putExtra("pBC",item)
            holder.itemView.context.startActivity(intent)
//            notifyDataSetChanged()
        }

        holder.binding.tvCategory.text = item.title
        Glide.with(holder.itemView.context).load(Constants.HOST_IMAGE+item.icon).into(holder.binding.imgCategory)
//        if (item.checked){
//            holder.itemView.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.colorPrimary))
//            holder.binding.tvCategory.setTextColor(Color.WHITE)
//        }else{
//            holder.itemView.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.grey))
//            holder.binding.tvCategory.setTextColor(Color.BLACK)
//        }
    }

    override fun getItemCount(): Int {
        return items.count()
    }
}