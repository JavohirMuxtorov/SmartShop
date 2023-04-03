package com.example.smartshop.fragments

import android.content.Intent
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
import com.example.smartshop.activity.MakeOrderActivity
import com.example.smartshop.databinding.FragmentCartBinding
import com.example.smartshop.databinding.FragmentFavoriteBinding
import com.example.smartshop.utils.PrefUtils
import com.example.smartshop.view.CartAdapter

class CartFragment : Fragment() {
    lateinit var binding: FragmentCartBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.progress.observe(this, Observer {
            binding.swipe.isRefreshing = it

        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(requireActivity(),it,Toast.LENGTH_LONG).show()
        })

        viewModel.topProductData.observe(this, Observer {
            binding.recycler.adapter = CartAdapter(it)

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.layoutManager = LinearLayoutManager(requireActivity())
        binding.swipe.setOnRefreshListener{
            loadData()
        }
        binding.btnMakeOrder.setOnClickListener {
            startActivity(Intent(requireActivity(), MakeOrderActivity::class.java))
        }
        loadData()
    }

    fun loadData(){
        viewModel.getProductsByIds(PrefUtils.getCartList().map { it.product_id})


    }

    companion object {

        @JvmStatic
        fun newInstance() = CartFragment()
    }
}