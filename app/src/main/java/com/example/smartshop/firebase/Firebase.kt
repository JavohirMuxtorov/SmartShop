package com.example.smartshop.firebase

import android.app.Activity
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.smartshop.MainActivity
import com.example.smartshop.activity.*
import com.example.smartshop.model.HistoryModel
import com.example.smartshop.model.UserModel
import com.example.smartshop.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

@RequiresApi(Build.VERSION_CODES.Q)
class Firebase {

    private val fireStore = FirebaseFirestore.getInstance()


    @RequiresApi(Build.VERSION_CODES.Q)
    fun signUp(activity: SignActivity, userInfo: UserModel) {
        fireStore.collection(Constants.USERS)
            .document(UserId()).set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegisteredSuccess()
            }
            .addOnFailureListener { e ->
                Log.e(activity.javaClass.simpleName, "error waiting for document", e)
            }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun userDetail(activity: Activity){
        fireStore.collection(Constants.USERS)
            .document(UserId())
            .get()
            .addOnSuccessListener {document->
                val loggedInUser = document.toObject(UserModel::class.java)!!
                when(activity){
                    is LogInActivity ->{
                        activity.logInSuccess(loggedInUser)
                    }
                    is MainActivity ->{
                        activity.userProfile(loggedInUser)
                    }
                    is EditNameActivity ->{
                        activity.userName(loggedInUser)
                    }
                    is SettingsActivity ->{
                        activity.editUserDetails(loggedInUser)
                    }
                }

            }
            .addOnFailureListener { e ->
                Log.e(activity.javaClass.simpleName, "error waiting for document", e)
            }

    }
    @RequiresApi(Build.VERSION_CODES.Q)
    fun updateUserProfileData(activity: Activity, userHashMap: HashMap<String, Any>) {
        fireStore.collection(Constants.USERS)
            // The document id to get the Fields of user.
            .document(UserId())
            .update(userHashMap)
            .addOnSuccessListener {
                when(activity){
                    is EditNameActivity ->{
                        activity.profileUpdatesSuccess()
                    }
                    is SettingsActivity ->{
                        activity.profileImgEditSuccess()
                    }
                }
            }.addOnFailureListener { e ->
                when (activity){
                    is EditNameActivity->{
                        activity.hideProgressDialog()
                    }
                    is SettingsActivity ->{
                        activity.hideProgressDialog()
                    }
                    is ChangeOpenPasswordActivity ->{
                        activity.hideProgressDialog()
                    }

                }
                Toast.makeText(activity, "Error when updating the profile name!", Toast.LENGTH_SHORT)
                    .show()
            }

    }


    fun UserId(): String {
        var currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID
    }
}