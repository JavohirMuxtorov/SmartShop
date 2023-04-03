package com.example.smartshop.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.smartshop.MainActivity
import com.example.smartshop.R
import com.example.smartshop.databinding.ActivityAboutDetailBinding
import com.example.smartshop.fragments.CartFragment
import com.example.smartshop.fragments.CommunityFragment
import com.r0adkll.slidr.Slidr
import com.r0adkll.slidr.model.SlidrInterface
import java.io.*

@RequiresApi(Build.VERSION_CODES.Q)
class AboutDetailActivity : AppCompatActivity() {
    lateinit var binding:ActivityAboutDetailBinding
    private var language:String = ""
    private lateinit var slidr: SlidrInterface
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
        slidr = Slidr.attach(this)
        slidr.unlock()
        binding.llBack.setOnClickListener {
            finish()
        }
        binding.text.setText(bundle!!.getCharSequence("child"))
        readPassword()
        if (language == "en"){
            if (binding.text.text == "Privacy Policy"){
                val htmlContent = assets.open("privarcy_en.html").bufferedReader().use {
                    it.readText()
                }
                binding.text2.text= Html.fromHtml(htmlContent)
            }
            else if (binding.text.text == "Product purchase rules") {
                val htmlContent = assets.open("qoida_en.html").bufferedReader().use {
                    it.readText()
                }
                binding.text2.text = Html.fromHtml(htmlContent)
            }
            else if (binding.text.text == "Community"){
                finish()
                val intent = Intent(this@AboutDetailActivity,MainActivity::class.java)
                intent.putExtra("community","communityFragment")
                startActivity(intent)
            }
            else if (binding.text.text == "Github"){
                finish()
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/JavohirMuxtorov/SmartShop"))
                startActivity(intent)
            }
            else if (binding.text.text == "Telegram"){
                finish()
                val channelName = "Smart Mobile Developers"
                val channelUserName = "android_dev_dangara"
                val channelId = "1619957139"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/$channelUserName"))
                startActivity(intent)
            }
            else if (binding.text.text == "How do I purchase the product?") {
                val htmlContent = assets.open("savol1_en.html").bufferedReader().use {
                    it.readText()
                }
                binding.text2.text = Html.fromHtml(htmlContent)
            }
            else if (binding.text.text == "How long does it take for the product to arrive?") {
                val htmlContent = assets.open("savol2_en.html").bufferedReader().use {
                    it.readText()
                }
                binding.text2.text = Html.fromHtml(htmlContent)
            }
            else if (binding.text.text == "What to do if the purchased Product does not arrive?") {
                val htmlContent = assets.open("savol3_en.html").bufferedReader().use {
                    it.readText()
                }
                binding.text2.text = Html.fromHtml(htmlContent)
            }
        }
        else if (language == "uz"){
            if (binding.text.text == "Maxfiylik Siyosati") {
                val htmlContent = assets.open("privarcy_uz.html").bufferedReader().use {
                    it.readText()
                }
                binding.text2.text = Html.fromHtml(htmlContent)
            }
            else if (binding.text.text == "Maxsulot sotib olish qoidalari") {
                val htmlContent = assets.open("qoida_uz.html").bufferedReader().use {
                    it.readText()
                }
                binding.text2.text = Html.fromHtml(htmlContent)
            }
            else if (binding.text.text == "Mahsulotni qanday sotib olaman?") {
                val htmlContent = assets.open("savol1_uz.html").bufferedReader().use {
                    it.readText()
                }
                binding.text2.text = Html.fromHtml(htmlContent)
            }
            else if (binding.text.text == "Mahsulot qancha vaqt ichida yetib keladi?") {
                val htmlContent = assets.open("savol2_uz.html").bufferedReader().use {
                    it.readText()
                }
                binding.text2.text = Html.fromHtml(htmlContent)
            }
            else if (binding.text.text == "Sotib olingan Mahsulot yetib kelmasa nima qilish kerak?") {
                val htmlContent = assets.open("savol3_uz.html").bufferedReader().use {
                    it.readText()
                }
                binding.text2.text = Html.fromHtml(htmlContent)
            }
            else if (binding.text.text == "Community"){
                finish()
                val intent = Intent(this@AboutDetailActivity,MainActivity::class.java)
                intent.putExtra("community","communityFragment")
                startActivity(intent)
            }
            else if (binding.text.text == "Github"){
                finish()
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/JavohirMuxtorov/SmartShop"))
                startActivity(intent)
            }
            else if (binding.text.text == "Telegram"){
                finish()
                val channelName = "Smart Mobile Developers"
                val channelUserName = "android_dev_dangara"
                val channelId = "1619957139"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/$channelUserName"))
                startActivity(intent)
            }
        }
        else if (language == "ru"){
            if (binding.text.text == "политика конфиденциальности") {
                val htmlContent = assets.open("privarcy_ru.html").bufferedReader().use {
                    it.readText()
                }
                binding.text2.text = Html.fromHtml(htmlContent)
            }
            else if (binding.text.text == "Правила покупки продукта") {
                val htmlContent = assets.open("qoida_ru.html").bufferedReader().use {
                    it.readText()
                }
                binding.text2.text = Html.fromHtml(htmlContent)
            }
            else if (binding.text.text == "Как приобрести продукт?") {
                val htmlContent = assets.open("savol1_ru.html").bufferedReader().use {
                    it.readText()
                }
                binding.text2.text = Html.fromHtml(htmlContent)
            }
            else if (binding.text.text == "Сколько времени занимает доставка товара?") {
                val htmlContent = assets.open("savol2_ru.html").bufferedReader().use {
                    it.readText()
                }
                binding.text2.text = Html.fromHtml(htmlContent)
            }
            else if (binding.text.text == "Что делать, если купленный Товар не пришел?") {
                val htmlContent = assets.open("savol3_ru.html").bufferedReader().use {
                    it.readText()
                }
                binding.text2.text = Html.fromHtml(htmlContent)
            }
            else if (binding.text.text == "Community"){
                finish()
                val intent = Intent(this@AboutDetailActivity,MainActivity::class.java)
                intent.putExtra("community","communityFragment")
                startActivity(intent)
            }
            else if (binding.text.text == "Github"){
                finish()
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/JavohirMuxtorov/SmartShop"))
                startActivity(intent)
            }
            else if (binding.text.text == "Telegram"){
                finish()
                val channelName = "Smart Mobile Developers"
                val channelUserName = "android_dev_dangara"
                val channelId = "1619957139"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/$channelUserName"))
                startActivity(intent)
            }





        }
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