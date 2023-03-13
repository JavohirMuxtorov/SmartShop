package com.example.smartshop.firebase

import android.app.Activity
import android.util.Log
import com.example.smartshop.MainActivity
import com.example.smartshop.activity.LogInActivity
import com.example.smartshop.activity.SignActivity
import com.example.smartshop.model.UserModel
import com.example.smartshop.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class Firebase {

    private val fireStore = FirebaseFirestore.getInstance()


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

    fun logIn(activity: Activity){
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
                }

            }
            .addOnFailureListener { e ->
                Log.e(activity.javaClass.simpleName, "error waiting for document", e)
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