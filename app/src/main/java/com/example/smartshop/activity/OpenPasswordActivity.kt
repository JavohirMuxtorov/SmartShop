package com.example.smartshop.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.smartshop.MainActivity
import com.example.smartshop.databinding.ActivityOpenPasswordBinding
import com.example.smartshop.firebase.Firebase
import com.example.smartshop.model.UserModel
import com.example.smartshop.utils.Constants
import com.hanks.passcodeview.PasscodeView
import com.hanks.passcodeview.PasscodeView.PasscodeViewListener
import java.io.*

@RequiresApi(Build.VERSION_CODES.Q)
class OpenPasswordActivity : AppCompatActivity() {
    lateinit var binding: ActivityOpenPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpenPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        readPassword()
        var passcode = binding.tvPasscode.text
        Toast.makeText(this, "${passcode}", Toast.LENGTH_LONG).show()
        binding.passcodeView.setPasscodeLength(4)
            .setLocalPasscode("$passcode").listener = object : PasscodeViewListener{
            override fun onFail() {

            }

            override fun onSuccess(number: String?) {
                startA()
            }

        }
    }
    fun startA(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
    private fun readPassword(){
        try {
            val fileRead = File(cacheDir,"passcode")
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
            binding.tvPasscode.text = stringBuilder

        }catch (exp: IOException){
            exp.printStackTrace()
        }
    }
}