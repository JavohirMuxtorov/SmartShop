package com.example.smartshop

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.smartshop.activity.AboutActivity
import com.example.smartshop.activity.BaseActivity
import com.example.smartshop.activity.OpenPasswordActivity
import com.example.smartshop.activity.SettingsActivity
import com.example.smartshop.databinding.ActivityMainBinding
import com.example.smartshop.firebase.Firebase
import com.example.smartshop.fragments.CartFragment
import com.example.smartshop.fragments.FavoriteFragment
import com.example.smartshop.fragments.HomeFragment
import com.example.smartshop.fragments.CommunityFragment
import com.example.smartshop.model.*
import com.example.smartshop.utils.Constants
import com.example.smartshop.utils.LocaleManager
import com.example.smartshop.view.CustomSpinnerAdapter
import com.example.smartshop.view.HistoryAdapter
import com.example.smartshop.view.HistoryProductAdapter
import com.orhanobut.hawk.Hawk
import com.squareup.picasso.Picasso
import java.io.*
import java.util.*
import kotlin.collections.ArrayList

@RequiresApi(Build.VERSION_CODES.Q)
class MainActivity : BaseActivity(), HistoryAdapter.OnItemClickListener {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    private var mSearchProduct = ArrayList<TopProductModel>()
    private var nightMode: Boolean = false
    private var editor: SharedPreferences.Editor? = null
    private var sharedPreferences: SharedPreferences? = null
    private var mHistoryList = ArrayList<HistoryModel>()
    private lateinit var adapter2: HistoryAdapter
    private lateinit var productAdapter: HistoryProductAdapter
    private var start: String = Constants.startPassword
    private var night: String = ""
    var nl: String = ""
    var strData: String =""
    val homeFragment = HomeFragment.newInstance()
    val favoriteFragment = FavoriteFragment.newInstance()
    val cartFragment = CartFragment.newInstance()
    val communityFragment = CommunityFragment.newInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var selectLanguage = resources.getString(R.string.lang)
        var language = arrayOf(selectLanguage,"ENGLISH", "O'ZBEK", "РУССКИЙ")
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.navLayout.settings.setOnClickListener {
            startActivityForResult(Intent(this, SettingsActivity::class.java), Constants.MY_PROFILE_REQUEST_CODE)
        }
        binding.navLayout.aboutApp.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }
        readPassword()
        if (start == "true"){
            binding.imgLock.visibility = View.VISIBLE
        }else{
            binding.imgLock.visibility = View.GONE
        }
        binding.llLock.setOnClickListener {
            if (start == "true") {
                startActivity(Intent(this, OpenPasswordActivity::class.java))
                finish()
            }
        }
        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        binding.searchProduct.layoutManager = LinearLayoutManager(this)
        productAdapter = HistoryProductAdapter(mSearchProduct)
        binding.searchProduct.adapter = productAdapter
        searchProduct()
        loadData()

        binding.search.setOnClickListener {
            binding.ll.visibility = View.GONE
            binding.searchProduct.visibility = View.GONE
            binding.searchHistory.visibility = View.VISIBLE
            binding.llSearchView.visibility = View.VISIBLE
        }
        binding.searchHistory.layoutManager = LinearLayoutManager(this)
        adapter2 = HistoryAdapter(mHistoryList, this)
        binding.searchHistory.adapter = adapter2
        binding.back.setOnClickListener {
            binding.ll.visibility = View.VISIBLE
            binding.llSearchView.visibility = View.GONE
        }
        if (binding.languagetv.text == "uz"){
            saveLanguage()
        }else if (binding.languagetv.text == "en"){
            saveLanguage()
        }else if (binding.languagetv.text == "ru"){
            saveLanguage()
        }
        val intentAD = intent.getStringExtra("community")
        if (intentAD == "communityFragment"){
            supportFragmentManager.beginTransaction()
                .add(R.id.flContainer, communityFragment, communityFragment.tag).commit()
        }else {
            supportFragmentManager.beginTransaction()
                .add(R.id.flContainer, homeFragment, homeFragment.tag).commit()
        }
        binding.bottomNavigationView.setOnItemSelectedListener {
            if (it.itemId == R.id.home) {
                supportFragmentManager.beginTransaction().replace(R.id.flContainer, homeFragment)
                    .commit()
            } else if (it.itemId == R.id.favorite) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.flContainer, favoriteFragment).commit()
            } else if (it.itemId == R.id.cart) {
                supportFragmentManager.beginTransaction().replace(R.id.flContainer, cartFragment)
                    .commit()
            } else if (it.itemId == R.id.map) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.flContainer, communityFragment).commit()
            }
            return@setOnItemSelectedListener true
        }
        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE)
        nightMode = sharedPreferences?.getBoolean("night", false) !!
        editor = sharedPreferences?.edit()
        if (nightMode) {
            binding.navLayout.themeSwitch.isChecked = true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        binding.navLayout.themeSwitch.setOnCheckedChangeListener { compoundButton, state ->
            saveLight()
            if (nightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor = sharedPreferences?.edit()
                editor?.putBoolean("night", false)
                binding.nightTv.text = "light"
                binding.navLayout.tvTheme.text = resources.getString(R.string.theme_light)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor = sharedPreferences?.edit()
                editor?.putBoolean("night", true)
                binding.nightTv.text = "dark"
                binding.navLayout.tvTheme.text = resources.getString(R.string.theme_dark)
            }
            editor?.apply()
        }
        if (binding.navLayout.themeSwitch.isChecked == true){
            binding.navLayout.tvTheme.text = resources.getString(R.string.theme_dark)
            binding.nightTv.text = "dark"
        }else if (binding.navLayout.themeSwitch.isChecked == false){
            binding.navLayout.tvTheme.text = resources.getString(R.string.theme_light)
            binding.nightTv.text = "light"
        }
        //
        Firebase().userDetail(this)
        binding.menu.setOnClickListener {
            binding.drawerLayout.openDrawer(binding.navView)
        }
        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchHistory.visibility = View.GONE
                binding.searchProduct.visibility = View.VISIBLE
                mHistoryList.add(HistoryModel(query.toString()))
                filterProductList(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterHistoryList(newText)
                binding.searchHistory.visibility = View.VISIBLE
                binding.searchProduct.visibility = View.GONE
                return true
            }
        })
        val bindings = binding.navLayout
bindings.shareApp.setOnClickListener {
    shareApp()
}
        val adapter = CustomSpinnerAdapter(this,language)
        bindings.spinner.adapter = adapter
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
                    binding.languagetv.text = "en"
                    finish()
                } else if (position == 2) {

                    Hawk.put("pref_lang", "uz")
                    startActivity(Intent(this@MainActivity, MainActivity::class.java))
                    binding.languagetv.text = "uz"
                    finish()

                } else if (position == 3) {
                    Hawk.put("pref_lang", "ru")
                    startActivity(Intent(this@MainActivity, MainActivity::class.java))
                    binding.languagetv.text = "ru"
                    finish()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun shareApp(){
        val appMSG: String = "Hello, I am using the Smart Shop application, I recommend you to download it from this link: " +
                "https://play.google.com/store/apps/details?id=com.example.smartshop"
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, appMSG)
        intent.type = "text/plain"
        startActivity(intent)
    }
    private fun saveLanguage(){
        try {
            val fileSave = File(cacheDir,"language")
            val fileOutputStream = FileOutputStream(fileSave)
            strData = binding.languagetv.text.toString()
            fileOutputStream.write(strData.toByteArray())
            fileOutputStream.close()
        }catch (exp: IOException){
            exp.printStackTrace()
        }
    }
    private fun saveLight(){
        try {
            val fileSave = File(cacheDir,"night")
            val fileOutputStream = FileOutputStream(fileSave)
            strData = binding.nightTv.text.toString()
            fileOutputStream.write(strData.toByteArray())
            fileOutputStream.close()
        }catch (exp: IOException){
            exp.printStackTrace()
        }
    }

    fun userProfile(user: UserModel) {
        Picasso.get().load(user.img).placeholder(R.drawable.user).into(binding.navLayout.ivUserImage)
        binding.navLayout.tvUserName.text = user.name
        binding.navLayout.tvEmail.text = user.email
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase))
    }

    private fun loadData() {
        viewModel.getHistoryProducts()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.MY_PROFILE_REQUEST_CODE){
            Firebase().userDetail(this)
            val data = data?.getStringExtra("result")
            start = data.toString()
        }else{
            Log.e("Cancelled","Cancelled")
        }
    }

    private fun readPassword(){
        try {
            val fileRead = File(cacheDir,"open")
            val fileInputStream = FileInputStream(fileRead)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder = StringBuilder()
            var line: String? = null
            while (run {
                    line = bufferedReader.readLine()
                    line
                } !=null){
                stringBuilder.append(line)
            }
            fileInputStream.close()
            start = stringBuilder.toString()

        }catch (exp: IOException){
            exp.printStackTrace()
        }
    }

    private fun filterHistoryList(query: String?) {
        if (query != null) {
            val filteredList = ArrayList<HistoryModel>()
            for (i in mHistoryList) {
                if (i.text.toLowerCase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()) {

            } else {
                adapter2.setFilteredList(filteredList)
            }
        }
    }

    private fun filterProductList(query: String?) {
        if (query != null) {
            val filteredList = ArrayList<TopProductModel>()
            for (i in mSearchProduct) {
                if (i.name.lowercase().contains(query.lowercase())) {
                    filteredList.add(i)
                }
            }
            productAdapter.setFilteredList(filteredList)
        }
    }

    fun searchProduct() {
        mSearchProduct.add(
            TopProductModel(
                1,
                "Смартфон iPhone 12 64GB Blue, White, Black, Green, Red",
                "9 434 000",
                "1614190223.jpg"
            )
        )
        mSearchProduct.add(
            TopProductModel(
                2,
                "Смартфон Xiaomi Redmi Note 9T 4/128GB Black (Global version)",
                "2 883 000",
                "1614190295.jpg"
            )
        )
        mSearchProduct.add(
            TopProductModel(
                3,
                "Смартфон iPhone 12 Pro max 128GB Black",
                "13 356 000",
                "1614190403.jpg"
            )
        )
        mSearchProduct.add(
            TopProductModel(
                4,
                "Смартфон iPhone 12 mini 64GB Black",
                "8 692 000",
                "1614190476.jpg"
            )
        )
        mSearchProduct.add(
            TopProductModel(
                5,
                "Смартфон Samsung Galaxy A51 64GB White, Blue, Black",
                "2 985 000",
                "1614190534.jpg"
            )
        )
        mSearchProduct.add(
            TopProductModel(
                6,
                "Планшет Samsung Galaxy Tab A 10.1 Black, Gold",
                "2 597 000",
                "1614190741.jpg"
            )
        )
        mSearchProduct.add(
            TopProductModel(
                7,
                "Планшет Samsung Galaxy Tab S6 10.5 4G Gray, Blue, Rose",
                "7 102 000",
                "1614190789.jpg"
            )
        )
        mSearchProduct.add(
            TopProductModel(
                8,
                "Планшет Apple iPad (2019) 32Gb Wi-Fi+4G Gray, Silver",
                "5 565 000",
                "1614190868.jpg"
            )
        )
        mSearchProduct.add(
            TopProductModel(
                9,
                "Планшет Apple iPad (2020) 128Gb Wi-Fi Gray, Silver, Gold",
                "5 459 000",
                "1614190973.jpg"
            )
        )
        mSearchProduct.add(
            TopProductModel(
                10,
                "Планшет Apple iPad Air (2020) 256Gb Wi-Fi Gray, Rose, Blue",
                "8 957 000",
                "1614191045.jpg"
            )
        )
        mSearchProduct.add(
            TopProductModel(
                11,
                "Смарт браслет Xiaomi Mi Band 5 (Русское Меню)",
                "329 000",
                "1614191225.jpg"
            )
        )
        mSearchProduct.add(
            TopProductModel(
                12,
                "Смарт часы HUAWEI Watch Fit Black",
                "1 240 000",
                "1614191274.jpg"
            )
        )
        mSearchProduct.add(
            TopProductModel(
                13,
                "Смарт часы Samsung Galaxy Watch Active 2 44",
                "2 141 000",
                "1614191333.jpg"
            )
        )
        mSearchProduct.add(
            TopProductModel(
                14,
                "Смарт часы Samsung Galaxy Watch Active 2 (сталь) 44 мм Gold, Black",
                "2 777 000",
                "1614191399.jpg"
            )
        )
        mSearchProduct.add(
            TopProductModel(
                15,
                "Смарт часы Xiaomi Amazfit GTR 47 mm Black, Silver",
                "1 272 000",
                "1614191455.jpg"
            )
        )
        mSearchProduct.add(
            TopProductModel(
                16,
                "Ноутбук Asus S432F \\/ Intel I5-8265 \\/ DDR4 8GB \\/ SSD 512GB \\/ 14\\\" FHD \\/ Win 10",
                "9 063 000",
                "1614191589.jpg"
            )
        )
        mSearchProduct.add(
            TopProductModel(
                17,
                "Ноутбук Acer A315-57G-3104 \\/ Intel i3-1005G1 \\/ DDR4 4GB \\/ HDD 1TB \\/ VGA 2GB \\/ 15.6\\\" HD LED",
                "6 201 000",
                "1614191660.jpg"
            )
        )
        mSearchProduct.add(
            TopProductModel(
                18,
                "Ноутбук HP Envy X360 15 \\/ Intel Core i7-10510 \\/ DDR4 8GB \\/ SSD 512GB \\/ 15.6\\u2033 Full HD IPS, TouchScreen \\/ Win 10",
                "12 137 000",
                "1614191724.jpg"
            )
        )
        mSearchProduct.add(
            TopProductModel(
                19,
                "Ноутбук Lenovo Yoga 730-15IWL \\/ Intel i5-8265U \\/ DDR4 8GB \\/ SSD 256Gb \\/ 15.6\\\" FHD IPS \\/ Wn10",
                "11 077 000",
                "1614191790.jpg"
            )
        )
        mSearchProduct.add(
            TopProductModel(
                20,
                "Ноутбук DELL Inspiron 3593 \\/ Intel i7-1065G7 \\/ DDR4 12GB \\/ SSD 512GB \\/ 15.6\\\" TN \\/ Win 10",
                "10 017 000",
                "1614191920.jpg"
            )
        )
        mSearchProduct.add(
            TopProductModel(
                21,
                "Монитор LG 24 24MP59G-P LED Gaming Monitor HDMI",
                "1 961 000",
                "1614192919.jpg"
            )
        )
        mSearchProduct.add(
            TopProductModel(
                22,
                "Монитор LG 24 IPS 34UM69G-B LED Gaming Monitor HDMI",
                "4 452 000",
                "1614192971.jpg"
            )
        )
        mSearchProduct.add(
            TopProductModel(
                23,
                "Монитор LG 24 27UL500 LED Monitor HDMI (5mc, UHD, 3840x2160, 4K) White",
                "4 028 000",
                "1614193020.jpg"
            )
        )
        mSearchProduct.add(
            TopProductModel(
                24,
                "Монитор LG 24 27UL850 LED Monitor HDMI (UHD 3840x2160)",
                "6 360 000",
                "1614193068.jpg"
            )
        )
        mSearchProduct.add(
            TopProductModel(
                25,
                "Монитор LG 24 32GK650F Gaming LED WQHD (2560x1440) 144 Ghz, (HDMI x2, DisplayPort) Black",
                "5 353 000",
                "1614193124.jpg"
            )
        )
        mSearchProduct.add(
            TopProductModel(
                26,
                "Клавиатура с мышью Rapoo X1810 Keyboard & Mouse Combo",
                "209 000",
                "1614193226.jpg"
            )
        )
        mSearchProduct.add(
            TopProductModel(
                27,
                "Клавиатура и мышью 2E MK410",
                "185 000",
                "1614193315.jpg"
            )
        )
        mSearchProduct.add(
            TopProductModel(
                28,
                "Клавиатура HyperX Alloy Origins RGB Red",
                "1 290 000",
                "1614193362.jpg"
            )
        )
        mSearchProduct.add(TopProductModel(29, "Мышью 2Е MF209 WL Black", "117 000", "1614193414.jpg"))
        mSearchProduct.add(
            TopProductModel(
                30,
                "Клавиатура Defender HM-830 Black USB",
                "95 000",
                "1614193484.jpg"
            )
        )
    }

    override fun onItemClick(position: Int) {
        binding.searchHistory.visibility = View.GONE
        binding.searchProduct.visibility = View.VISIBLE
        binding.searchView.setQuery(mHistoryList[position].text, true)
    }
}