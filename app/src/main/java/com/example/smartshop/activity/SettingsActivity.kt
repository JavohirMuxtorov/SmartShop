package com.example.smartshop.activity

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.smartshop.MainActivity
import com.example.smartshop.R
import com.example.smartshop.databinding.ActivitySettingsBinding
import com.example.smartshop.firebase.Firebase
import com.example.smartshop.model.PasscodeModel
import com.example.smartshop.model.UserModel
import com.example.smartshop.utils.Constants
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.r0adkll.slidr.Slidr
import com.r0adkll.slidr.model.SlidrInterface
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.io.*

@RequiresApi(Build.VERSION_CODES.Q)
class SettingsActivity : BaseActivity() {
    private var mSelectedImageFileUri: Uri? = null
    private lateinit var mUserDetails: UserModel
    private var mProfileImageURL: String = ""
    private var start: String = "false"
    private var start2: String = ""
    lateinit var binding: ActivitySettingsBinding
    private lateinit var slidr: SlidrInterface
    var passcode: PasscodeModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Firebase().userDetail(this)

        slidr = Slidr.attach(this)
        slidr.unlock()
        binding.llBack.setOnClickListener {
            finish()
        }
        binding.llName.setOnClickListener {
            startActivityForResult(Intent(this, EditNameActivity::class.java),Constants.MY_PROFILE_REQUEST_CODE)
        }
        readPassword()

        if (! EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }
        if (start == "true"){
            binding.tvOpenPassword.text = resources.getString(R.string.kirish_paroli_on)
        }else{
            binding.tvOpenPassword.text = resources.getString(R.string.kirish_paroli_off)
        }
        binding.llOpenPassword.setOnClickListener {
            if (start == "true"){
                startActivityForResult(Intent(this,ChangeOpenPasswordSettingsActivity::class.java),Constants.MY_PROFILE_REQUEST_CODE)
            }else{
                startActivityForResult(Intent(this,ChangeOpenPasswordActivity::class.java), Constants.MY_PROFILE_REQUEST_CODE)
            }
        }
        binding.addImage.setOnClickListener {
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                VersionM()
            }else if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
                VersionR()
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.R)
    private fun VersionR(){
        if (Environment.isExternalStorageManager()) {
            Constants.showImageChooser(this)
        } else {
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                data = Uri.parse("package:" + applicationContext.packageName)
            }
            startActivityForResult(intent, Constants.READ_STORAGE_PERMISSION_CODE)
        }
    }
private fun VersionM(){
    if (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        Constants.showImageChooser(this)
    }
    else {
        val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        val grant = ContextCompat.checkSelfPermission(this, permission)
        if (grant != PackageManager.PERMISSION_GRANTED) {
            val permissionList = arrayOf(permission)
            ActivityCompat.requestPermissions(
                this,
                permissionList,
                Constants.READ_STORAGE_PERMISSION_CODE
            )
        }
    }
}
    override fun onDestroy() {
        super.onDestroy()

        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this)
        }
    }
    @Subscribe
    fun onEvent(passcodeModel: PasscodeModel){
        this.passcode = passcodeModel
        binding.tvOpenPassword.text = passcodeModel.text
        start=passcodeModel.text2
        start2 = passcodeModel.text2
        val intent = Intent()
        intent.putExtra("result",intent)
        setResult(Activity.RESULT_OK,intent)
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
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Constants.showImageChooser(this)
            }
        } else {
            Toast.makeText(
                this,
                "Oops, you just denied the permission for storage. You can also allow it from settings",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.MY_PROFILE_REQUEST_CODE) {
            Firebase().userDetail(this)
        } else {
            Log.e("Cancelled", "Cancelled")
        }
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.PICK_IMAGE_REQUEST_CODE && data!!.data != null) {
            mSelectedImageFileUri = data.data
            if (mSelectedImageFileUri != null) {
                uploadUserImage()
            } else {
                showProgressDialog()
                updateUserProfileData()
            }
            try {
                Glide
                    .with(this@SettingsActivity)
                    .load(mUserDetails.img)
                    .centerCrop()
                    .placeholder(R.drawable.user)
                    .into(binding.imgProfile)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R &&
                Environment.isExternalStorageManager()) {
                Constants.showImageChooser(this)
            } else {
                Toast.makeText(
                    this,
                    "Oops, you just denied the permission for storage. You can also allow it from settings",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun updateUserProfileData() {
        val userHashMap = HashMap<String, Any>()
        var anyChangesMode = false
        if (binding.tvName.text != mUserDetails.name){
            userHashMap[Constants.NAME] = binding.tvName.text
            anyChangesMode = true
        }
        if (mProfileImageURL.isNotEmpty() && mProfileImageURL != mUserDetails.img) {
            userHashMap[Constants.IMG] = mProfileImageURL
            anyChangesMode = true
        }
        if (anyChangesMode) {
            Firebase().updateUserProfileData(this, userHashMap)
        }
    }
    private fun uploadUserImage() {

        showProgressDialog()

        if (mSelectedImageFileUri != null) {

            val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
                "USER_IMAGE" + System.currentTimeMillis() + "." + Constants.getFileExtensions(
                    this,
                    mSelectedImageFileUri
                )
            )

            sRef.putFile(mSelectedImageFileUri!!)
                .addOnSuccessListener { taskSnapshot ->
                    Log.e(
                        "Firebase Image URL",
                        taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                    )

                    taskSnapshot.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener { uri ->
                            Log.e("Downloadable Image URL", uri.toString())
                            Toast.makeText(this, "qo'shildi", Toast.LENGTH_LONG).show()
                            mProfileImageURL = uri.toString()
                            updateUserProfileData()

                        }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(
                        this@SettingsActivity,
                        exception.message,
                        Toast.LENGTH_LONG
                    ).show()
                    Log.e("erorr", exception.message.toString())

                    hideProgressDialog()
                }
        }
    }

    fun profileImgEditSuccess() {
        hideProgressDialog()
        setResult(Activity.RESULT_OK)
        finish()
    }
    fun editUserDetails(user: UserModel){
        mUserDetails = user
        Glide.with(this).load(user.img)
            .into(binding.imgProfile)
        binding.tvName.text =user.name
        binding.tvEmail.text = user.email

    }
}