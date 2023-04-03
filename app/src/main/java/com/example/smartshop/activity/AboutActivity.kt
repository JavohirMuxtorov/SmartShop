package com.example.smartshop.activity

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.smartshop.MainActivity
import com.example.smartshop.R
import com.example.smartshop.databinding.ActivityAboutBinding
import com.example.smartshop.fragments.CommunityFragment
import com.example.smartshop.view.ELAdapter
import com.r0adkll.slidr.Slidr
import com.r0adkll.slidr.model.SlidrInterface
import java.io.*

class AboutActivity : BaseActivity() {
    lateinit var binding: ActivityAboutBinding
    val title: MutableList<String> = ArrayList()
    val subTitle: MutableList<MutableList<String>> = ArrayList()
    private var language:String = ""
    private lateinit var slidr: SlidrInterface
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title.add(resources.getString(R.string.dasturHaqida))
        title.add(resources.getString(R.string.manba))
        title.add(resources.getString(R.string.savollar))
        slidr = Slidr.attach(this)
        slidr.unlock()

        val dasturHaqida: MutableList<String> = ArrayList()
        dasturHaqida.add(resources.getString(R.string.maxfiylik))
        dasturHaqida.add(resources.getString(R.string.qoidalar))
        dasturHaqida.add(resources.getString(R.string.communityAPP))

        val manbalar: MutableList<String> = ArrayList()
        manbalar.add(resources.getString(R.string.github))
        manbalar.add(resources.getString(R.string.telegram))

        val savollar: MutableList<String> = ArrayList()
        savollar.add(resources.getString(R.string.savol1))
        savollar.add(resources.getString(R.string.savol2))
        savollar.add(resources.getString(R.string.savol3))
        savollar.add(resources.getString(R.string.savol4))

        subTitle.add(dasturHaqida)
        subTitle.add(manbalar)
        subTitle.add(savollar)
        val adapter = ELAdapter(this, title,subTitle)
        binding.expandView.setAdapter(adapter)
        binding.expandView.setOnChildClickListener(object : ExpandableListView.OnChildClickListener{
            @RequiresApi(Build.VERSION_CODES.Q)
            override fun onChildClick(
                parent: ExpandableListView?,
                v: View?,
                groupPosition: Int,
                childPosition: Int,
                id: Long
            ): Boolean {
                readPassword()
                val success = adapter.getChild(groupPosition, childPosition)
                val intent = Intent(this@AboutActivity, AboutDetailActivity::class.java)
                val bundle = Bundle()
                if (language == "uz") {
                    when (success) {
                        dasturHaqida[0] -> {
                            bundle.putString("child", "Maxfiylik Siyosati")
                        }
                        dasturHaqida[1] -> {
                            bundle.putString("child", "Maxsulot sotib olish qoidalari")
                        }
                        dasturHaqida[2] -> {
                            bundle.putString("child", "Community")
                        }
                        manbalar[0]->{
                            bundle.putString("child", "Github")
                        }
                        manbalar[1]->{
                            bundle.putString("child", "Telegram")
                        }
                        savollar[0] ->{
                            bundle.putString("child", "Mahsulotni qanday sotib olaman?")
                        }
                        savollar[1] ->{
                            bundle.putString("child", "Mahsulot qancha vaqt ichida yetib keladi?")
                        }
                        savollar[2] ->{
                            bundle.putString("child", "Sotib olingan Mahsulot yetib kelmasa nima qilish kerak?")
                        }
                        savollar[3] ->{
                            bundle.putString("child", "Community")
                        }
                    }
                }
                else if (language == "en") {
                    when (success) {
                        dasturHaqida[0] -> {
                            bundle.putString("child", "Privacy Policy")
                        }
                        dasturHaqida[1] -> {
                            bundle.putString("child", "Product purchase rules")
                        }
                        dasturHaqida[2] -> {
                            bundle.putString("child", "Community")
                        }
                        manbalar[0]->{
                            bundle.putString("child", "Github")
                        }
                        manbalar[1]->{
                            bundle.putString("child", "Telegram")
                        }
                        savollar[0] ->{
                            bundle.putString("child", "How do I purchase the product?")
                        }
                        savollar[1] ->{
                            bundle.putString("child", "How long does it take for the product to arrive?")
                        }
                        savollar[2] ->{
                            bundle.putString("child", "What to do if the purchased Product does not arrive?")
                        }
                        savollar[3] ->{
                            bundle.putString("child", "Community")
                        }
                    }
                }
                else if (language == "ru") {
                    when (success) {
                        dasturHaqida[0] -> {
                            bundle.putString("child", "политика конфиденциальности")
                        }
                        dasturHaqida[1] -> {
                            bundle.putString("child", "Правила покупки продукта")
                        }
                        dasturHaqida[2] -> {
                            bundle.putString("child", "Community")
                        }
                        manbalar[0]->{
                            bundle.putString("child", "Github")
                        }
                        manbalar[1]->{
                            bundle.putString("child", "Telegram")
                        }
                        savollar[0] ->{
                            bundle.putString("child", "Как приобрести продукт?")
                        }
                        savollar[1] ->{
                            bundle.putString("child", "Сколько времени занимает доставка товара?")
                        }
                        savollar[2] ->{
                            bundle.putString("child", "Что делать, если купленный Товар не пришел?")
                        }
                        savollar[3] ->{
                            bundle.putString("child", "Community")
                        }
                    }
                }
                intent.putExtras(bundle)
                startActivity(intent)
                return true
            }
        })
    }
    private fun readPassword(){
        try {
            val fileRead = File(cacheDir,"language")
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
            language = stringBuilder.toString()

        }catch (exp: IOException){
            exp.printStackTrace()
        }
    }
}