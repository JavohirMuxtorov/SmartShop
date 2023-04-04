package com.example.smartshop

import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.GoogleMap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.SupportMapFragment
import com.example.smartshop.R
import com.example.smartshop.databinding.ActivityMapsBinding
import com.example.smartshop.model.AddressModel
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import org.greenrobot.eventbus.EventBus

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.btnConfirm.setOnClickListener{
            val AddressModel = AddressModel("",mMap.cameraPosition.target.latitude,mMap.cameraPosition.target.longitude)
            EventBus.getDefault().post(AddressModel)
            finish()
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }
}