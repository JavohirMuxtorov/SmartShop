package com.example.smartshop.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.smartshop.MainActivity
import com.example.smartshop.R
import com.example.smartshop.databinding.ActivityChangeOpenPasswordBinding
import com.example.smartshop.firebase.Firebase
import com.example.smartshop.model.PasscodeModel
import com.example.smartshop.model.UserModel
import com.example.smartshop.utils.Constants
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.greenrobot.eventbus.EventBus
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ChangeOpenPasswordActivity : BaseActivity() {
    lateinit var binding: ActivityChangeOpenPasswordBinding
    var strData: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeOpenPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cvNextCreate.setOnClickListener {
            binding.passwordTV.text = binding.etChangePassword.text.toString()
            binding.llCreate.visibility = View.GONE
            binding.llConfirm.visibility = View.VISIBLE
        }
        binding.cvNextConfirm.setOnClickListener {
            if (binding.etChangePasswordConfirm.text.toString() == binding.passwordTV.text){
                savePassword()
                passcode()
                var text = PasscodeModel(resources.getString(R.string.kirish_paroli_on),"true")
                EventBus.getDefault().post(text)
                var truetxt = "true"
                val intent2 = Intent()
                intent2.putExtra("true",truetxt)
                setResult(Activity.RESULT_OK,intent2)
                finish()
            }
            else{
                Toast.makeText(this, "xato", Toast.LENGTH_LONG).show()
            }
        }

    }
    private fun savePassword(){
        try {
            val fileSave = File(cacheDir,"passcode")
            val fileOutputStream = FileOutputStream(fileSave)
            strData = binding.etChangePasswordConfirm.text.toString()
            fileOutputStream.write(strData.toByteArray())
            fileOutputStream.close()
        }catch (exp: IOException){
            exp.printStackTrace()
        }
    }
    private fun passcode(){
        try {
            val fileSave = File(cacheDir,"open")
            val fileOutputStream = FileOutputStream(fileSave)
            strData = "true"
            fileOutputStream.write(strData.toByteArray())
            fileOutputStream.close()
            finish()
        }catch (exp: IOException){
            exp.printStackTrace()
        }
    }

}