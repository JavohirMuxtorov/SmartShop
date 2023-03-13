package com.example.smartshop

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.smartshop.activity.LogInActivity
import com.example.smartshop.databinding.ActivityMainBinding
import com.example.smartshop.firebase.Firebase
import com.example.smartshop.fragments.CartFragment
import com.example.smartshop.fragments.FavoriteFragment
import com.example.smartshop.fragments.HomeFragment
import com.example.smartshop.fragments.MapFragment
import com.example.smartshop.model.UserModel
import com.example.smartshop.utils.Constants
import com.example.smartshop.utils.LocaleManager
import com.orhanobut.hawk.Hawk

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var language = arrayOf("tilni tanlang","ENGLISH", "O'ZBEK", "РУССКИЙ")

    private var nightMode: Boolean=false
    private var editor: SharedPreferences.Editor?=null
    private var sharedPreferences: SharedPreferences?=null

    val homeFragment = HomeFragment.newInstance()
    val favoriteFragment = FavoriteFragment.newInstance()
    val cartFragment = CartFragment.newInstance()
    val mapFragment = MapFragment.newInstance()
    var activeFragment: Fragment = homeFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navLayout.logOutApp.setOnClickListener {
            startActivity(Intent(this,LogInActivity::class.java))
        }
        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, homeFragment, homeFragment.tag).hide(homeFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, favoriteFragment, favoriteFragment.tag).hide(favoriteFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, cartFragment, cartFragment.tag).hide(cartFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, mapFragment, mapFragment.tag).hide(mapFragment)
            .commit()

        supportFragmentManager.beginTransaction().show(activeFragment).commit()




        binding.bottomNavigationView.setOnItemSelectedListener {
            if (it.itemId == R.id.home){
                supportFragmentManager.beginTransaction().hide(activeFragment).show(homeFragment).commit()
                activeFragment = homeFragment
            }else if (it.itemId == R.id.favorite) {
                supportFragmentManager.beginTransaction().hide(activeFragment).show(favoriteFragment)
                    .commit()
                activeFragment = favoriteFragment
            } else if (it.itemId == R.id.cart) {
                supportFragmentManager.beginTransaction().hide(activeFragment).show(cartFragment)
                    .commit()
                activeFragment = cartFragment
            } else if (it.itemId == R.id.map) {
                supportFragmentManager.beginTransaction().hide(activeFragment).show(mapFragment)
                    .commit()
                activeFragment = mapFragment
            }


            return@setOnItemSelectedListener true
        }
//
        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE)
        nightMode = sharedPreferences?.getBoolean("night",false)!!
        if (nightMode){
            binding.navLayout.themeSwitch.isChecked = true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        binding.navLayout.themeSwitch.setOnCheckedChangeListener{compoundButton, state ->
            if (nightMode){

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor = sharedPreferences?.edit()
                editor?.putBoolean("night",false)
            }else{

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor = sharedPreferences?.edit()
                editor?.putBoolean("night",true)
            }
            editor?.apply()
        }


        //
        Firebase().logIn(this)
        binding.menu.setOnClickListener {
            binding.drawerLayout.openDrawer(binding.navView)
        }


        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item, language
        )
        binding.navLayout.spinner.adapter = adapter
        val bindings = binding.navLayout
        bindings.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 1) {

                        Hawk.put("pref_lang", "en")
                        startActivity(Intent(this@MainActivity, MainActivity::class.java))
                        finish()
                } else if (position == 2) {

                        Hawk.put("pref_lang", "uz")
                        startActivity(Intent(this@MainActivity, MainActivity::class.java))
                        finish()

                } else if (position == 3) {
                        Hawk.put("pref_lang", "ru")
                        startActivity(Intent(this@MainActivity, MainActivity::class.java))
                        finish()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }


    fun userProfile(user: UserModel) {
//        Glide.with(this).load(user.image)
//            .into(binding.navLayout.ivUserImage)
        binding.navLayout.tvUserName.text = user.name
        binding.navLayout.tvEmail.text = user.email
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase))
    }
}