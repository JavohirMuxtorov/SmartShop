package com.example.smartshop.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.smartshop.MapsActivity
import com.example.smartshop.R
import com.example.smartshop.databinding.ActivityMakeOrderBinding
import com.example.smartshop.model.AddressModel
import com.example.smartshop.model.TopProductModel
import com.example.smartshop.utils.Constants
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.io.IOException

class MakeOrderActivity : AppCompatActivity() {
    lateinit var binding: ActivityMakeOrderBinding
    var address:AddressModel? = null
    lateinit var items:List<TopProductModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakeOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        items = intent.getSerializableExtra(Constants.EXTRA_DATA) as List<TopProductModel>
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)

        }
        binding.total.text = calculateTotalPrice(items)
        binding.edAddress.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this)

        }
    }
    @Subscribe
    fun onEvent(address: AddressModel){
        this.address = address
        binding.edAddress.setText( "${address.latitude},${address.longitude}")

    }

    fun calculateTotalPrice(selectedProducts: List<TopProductModel>): String {
        var totalPrice:Long = 0
        try {
            for (product in selectedProducts) {
                val price = product.price.replace("\\s".toRegex(), "").toLong()
                val itemPrice = price * product.cartCount
                totalPrice += itemPrice
            }
        }catch (e: IOException){
            e.printStackTrace()
        }
        return totalPrice.toString()
    }
}