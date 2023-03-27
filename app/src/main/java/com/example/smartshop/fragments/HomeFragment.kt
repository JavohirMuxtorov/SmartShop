package com.example.smartshop.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartshop.MainViewModel
import com.example.smartshop.databinding.FragmentHomeBinding
import com.example.smartshop.view.CategoryAdapter
import com.example.smartshop.view.TopProductAdapter

class HomeFragment : Fragment() {
lateinit var viewModel: MainViewModel
lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerCategory.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerProduct.layoutManager = GridLayoutManager(requireActivity(),2)
        binding.swipe.setOnRefreshListener {
            loadData()
        }
        viewModel.progress.observe(requireActivity(), Observer {
            binding.swipe.isRefreshing = it
        })
        viewModel.error.observe(requireActivity(), Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })
        viewModel.categoriesData.observe(requireActivity(), Observer {
            binding.recyclerCategory.adapter = CategoryAdapter(it)
        })
        viewModel.topProductData.observe(requireActivity(), Observer {
            binding.recyclerProduct.adapter = TopProductAdapter(it)
        })


        loadData()
    }

    fun loadData() {
        viewModel.getCategories()
        viewModel.getTopProducts()
    }

    companion object {

        @JvmStatic
        fun newInstance() = HomeFragment()
    }

}