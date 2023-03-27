package com.example.smartshop.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap

object Constants {
    const val USERS: String = "users"
    const val NAME:String = "name"
    const val IMG:String = "img"
    const val READ_STORAGE_PERMISSION_CODE = 1
    const val MY_PROFILE_REQUEST_CODE: Int = 11
    const val PICK_IMAGE_REQUEST_CODE = 2
    var startPassword: String = ""
    const val MY_PASSCODE:Int = 7
    const val BASE_URL = "http://osonsavdo.devapp.uz/api/"
    const val HOST_IMAGE = "http://osonsavdo.devapp.uz/images/"
    fun getFileExtensions(activity: Activity, uri: Uri?): String? {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }
    fun showImageChooser(activity: Activity) {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)

    }
}