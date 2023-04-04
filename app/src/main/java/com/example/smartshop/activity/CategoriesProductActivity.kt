package com.example.smartshop.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.GridLayoutManager
import com.example.smartshop.MainActivity
import com.example.smartshop.MainViewModel
import com.example.smartshop.R
import com.example.smartshop.databinding.ActivityCategoriesProductBinding
import com.example.smartshop.fragments.ProductDetailFragment
import com.example.smartshop.model.CategoryModel
import com.example.smartshop.model.ProductModel
import com.example.smartshop.view.CategoryAdapter
import com.example.smartshop.view.ProductAdapter

class CategoriesProductActivity : AppCompatActivity(),ProductAdapter.OnItemClickListener {
    lateinit var binding: ActivityCategoriesProductBinding
    lateinit var item: CategoryModel
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriesProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        item = intent.getSerializableExtra("pBC") as CategoryModel
        binding.recyclerPBG.layoutManager = GridLayoutManager(this,2)
        viewModel.productData.observe(this, Observer {
            binding.recyclerPBG.adapter = ProductAdapter(it,this)
        })
        binding.back.setOnClickListener {
            finish()
        }
        val marquee = binding.tvTitle
        marquee.text = item.title
        marquee.setSingleLine()
        marquee.ellipsize = TextUtils.TruncateAt.MARQUEE
        marquee.marqueeRepeatLimit = -1
        marquee.isSelected = true
        loadData()
    }
    private fun loadData(){
        viewModel.getProductsByCategory(item.id)
    }

    override fun onItemClick(item: ProductModel) {
        val fragment = ProductDetailFragment.newInstance(item)
        fragment.show(supportFragmentManager,ProductDetailFragment.TAG)

    }
}