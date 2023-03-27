package com.example.smartshop.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.smartshop.R
import com.example.smartshop.databinding.ActivityEditNameBinding
import com.example.smartshop.firebase.Firebase
import com.example.smartshop.model.UserModel
import com.example.smartshop.utils.Constants
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.r0adkll.slidr.Slidr
import com.r0adkll.slidr.model.SlidrInterface

@RequiresApi(Build.VERSION_CODES.Q)
class EditNameActivity : BaseActivity() {
    private var mSelectedImageFileUri: Uri? = null
    private lateinit var mUserDetails: UserModel
    private var mProfileImageURL: String = ""
    private lateinit var slidr: SlidrInterface
    lateinit var binding: ActivityEditNameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        slidr = Slidr.attach(this)
        slidr.unlock()

        Firebase().userDetail(this)
        binding.tvSave.setOnClickListener {
            if (mSelectedImageFileUri != null) {
                uploadUserImage()
            } else {
                showProgressDialog()
                updateUserProfileData()
            }
        }
    }

    fun userName(user: UserModel){
        mUserDetails = user
        binding.etName.setText(user.name)
    }

    private fun updateUserProfileData() {
        val userHashMap = HashMap<String, Any>()
        var anyChangesMode = false
        if (binding.etName.text.toString() != mUserDetails.name) {
            userHashMap[Constants.NAME] = binding.etName.text.toString()
            anyChangesMode = true
        }
        if (anyChangesMode)
            Firebase().updateUserProfileData(this, userHashMap)

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

                            mProfileImageURL = uri.toString()
                            updateUserProfileData()

                        }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(
                        this@EditNameActivity,
                        exception.message,
                        Toast.LENGTH_LONG
                    ).show()
                    Log.e("erorr", exception.message.toString())

                    hideProgressDialog()
                }
        }
    }

    fun profileUpdatesSuccess() {
        hideProgressDialog()
        setResult(Activity.RESULT_OK)
        finish()
    }

}