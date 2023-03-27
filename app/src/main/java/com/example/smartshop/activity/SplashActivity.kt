package com.example.smartshop.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.smartshop.MainActivity
import com.example.smartshop.databinding.ActivitySplashBinding
import com.example.smartshop.firebase.Firebase
import com.example.smartshop.model.UserModel
import com.example.smartshop.utils.LocaleManager
import java.io.*

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
     var start: String = "false"
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        readPassword()
        binding.animationView.postDelayed({
            var currentUserID = Firebase().UserId()
            if (currentUserID.isNotEmpty() && start == "true"){
                startActivity(Intent(this,
                    OpenPasswordActivity::class.java))
            }else if (currentUserID.isNotEmpty() && start == "false"){
                startActivity(Intent(this, MainActivity::class.java))
            }else{
                startActivity(Intent(this, LogInActivity::class.java))
            }
            finish()
        },4800)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase))
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

}