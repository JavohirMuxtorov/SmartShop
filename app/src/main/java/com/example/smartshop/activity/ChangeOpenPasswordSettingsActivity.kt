package com.example.smartshop.activity

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.smartshop.MainActivity
import com.example.smartshop.R
import com.example.smartshop.databinding.ActivityChangeOpenPasswordSettingsBinding
import com.example.smartshop.model.PasscodeModel
import com.example.smartshop.utils.Constants
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.greenrobot.eventbus.EventBus
import java.io.*

@RequiresApi(Build.VERSION_CODES.Q)
class ChangeOpenPasswordSettingsActivity : AppCompatActivity() {
    private var start: String = ""
    lateinit var binding: ActivityChangeOpenPasswordSettingsBinding
    private lateinit var mProgressDialog: BottomSheetDialog
    var strData: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeOpenPasswordSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        readPassword()
        binding.cvNextTest.setOnClickListener {
            if(binding.testPassword.text.toString() == start){
                binding.llTest.visibility = View.GONE
                binding.llSettings.visibility = View.VISIBLE
            }
        }
        binding.cvBackTest.setOnClickListener {
            finish()
        }
        binding.deletePassword.setOnClickListener {
            deletePasscode()
        }
        binding.changePassword.setOnClickListener {
            startActivity(Intent(this, ChangeOpenPasswordActivity::class.java))
            finish()
        }
    }

private fun deletePasscode(){
    mProgressDialog = BottomSheetDialog(this, R.style.BottomSheetDialog)
    val bottomSheetView = LayoutInflater.from(applicationContext).inflate(
        R.layout.delete_passcode_dialog,
        findViewById(R.id.bottomSheetDialog1) as FrameLayout?
    )
    bottomSheetView.findViewById<CardView>(R.id.okDelete).setOnClickListener {
        savePassword()
        passcode()
        mProgressDialog.dismiss()
        val text = PasscodeModel(resources.getString(R.string.kirish_paroli_off), "false")
        EventBus.getDefault().post(text)
        var falsetxt = "false"
        val intent = Intent()
        intent.putExtra("false",falsetxt)
        setResult(Activity.RESULT_OK,intent)
        finish()
    }
    bottomSheetView.findViewById<CardView>(R.id.noDelete).setOnClickListener {
        mProgressDialog.dismiss()
    }
    mProgressDialog.setContentView(bottomSheetView)
    mProgressDialog.setCancelable(false)
    mProgressDialog.show()
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
            start = stringBuilder.toString()
            Constants.startPassword = stringBuilder.toString()

        }catch (exp: IOException){
            exp.printStackTrace()
        }
    }


    private fun savePassword(){
        try {
            val fileSave = File(cacheDir,"passcode")
            val fileOutputStream = FileOutputStream(fileSave)
            strData = ""
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
            strData = "false"
            fileOutputStream.write(strData.toByteArray())
            Constants.startPassword = strData
            fileOutputStream.close()
        }catch (exp: IOException){
            exp.printStackTrace()
        }
    }
}