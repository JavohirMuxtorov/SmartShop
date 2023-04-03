package com.example.smartshop.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartshop.MainViewModel
import com.example.smartshop.R
import com.example.smartshop.databinding.FragmentFavoriteBinding
import com.example.smartshop.utils.PrefUtils
import com.example.smartshop.view.FavoriteAdapter

class FavoriteFragment : Fragment() {
    lateinit var binding: FragmentFavoriteBinding
    lateinit var viewModel: MainViewModel
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.topProductData.observe(this, Observer {
            binding.recyclerProducts.adapter = FavoriteAdapter(it)

        })
        viewModel.error.observe(this, Observer {
            Toast.makeText(requireActivity(),it,Toast.LENGTH_LONG).show()
        })
        viewModel.progress.observe(this, Observer {
            binding.swipe.isRefreshing = it
        })

    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerProducts.layoutManager = LinearLayoutManager(requireActivity())

        binding.swipe.setOnRefreshListener {
            loadData()
        }
        loadData()
    }
    fun loadData(){
        viewModel.getProductsByIds(PrefUtils.getFavoriteList())
    }

    companion object {
        fun newInstance() = FavoriteFragment()
    }
}