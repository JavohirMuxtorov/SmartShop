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
import com.bumptech.glide.Glide
import com.example.smartshop.MainViewModel
import com.example.smartshop.R
import com.example.smartshop.databinding.FragmentHomeBinding
import com.example.smartshop.model.CategoryModel
import com.example.smartshop.utils.Constants
import com.example.smartshop.view.CategoryAdapter

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
        binding.recyclerCategory.layoutManager = GridLayoutManager(requireActivity(),4)
        viewModel.error.observe(requireActivity(), Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })
        viewModel.offersData.observe(requireActivity(), Observer {
            binding.carouselView.setImageListener { position, imageView ->
                Glide.with(imageView).load(Constants.HOST_IMAGE+it[position].image).into(imageView)
            }
            binding.carouselView.pageCount = it.count()
        })
        viewModel.categoriesData.observe(requireActivity(), Observer {
            binding.recyclerCategory.adapter = CategoryAdapter(it)
        })

        loadData()
    }

    fun loadData() {
        viewModel.getOffers()
        viewModel.getCategories()
    }

    companion object {

        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}