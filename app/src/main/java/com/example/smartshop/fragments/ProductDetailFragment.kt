package com.example.smartshop.fragments

import android.icu.text.Transliterator.Position
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.smartshop.R
import com.example.smartshop.databinding.FragmentProductDetailBinding
import com.example.smartshop.model.ProductModel
import com.example.smartshop.view.ProductAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ProductDetailFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentProductDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments.let {
            val item = it?.getParcelable<ProductModel>(ARG_ITEM)
            if (item != null) {
                binding.text.text = item.name
            }
        }

    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments.let {
//            val item = it?.getParcelable<ProductModel>(ARG_ITEM)
//            if (item != null) {
//                binding.text.text = item.name
//            }
//        }
//    }

    companion object {
        const val TAG = "ProductDetailFragment"
        const val ARG_ITEM = "item"
        fun newInstance(item: ProductModel) = ProductDetailFragment().apply {
            arguments = bundleOf(ARG_ITEM to item)
        }
    }
}