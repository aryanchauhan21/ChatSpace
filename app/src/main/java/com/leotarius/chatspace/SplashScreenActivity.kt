package com.leotarius.chatspace

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.leotarius.chatspace.models.User

public class SplashScreenActivity : AppCompatActivity() {

    private val TAG = "SplashScreenActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen)

        Log.d(TAG, "onCreate: wtf")
        val user = FirebaseAuth.getInstance().currentUser

        UtilFunctions.delay(1f, UtilFunctions.DelayCallback {
            if(user == null){
                startActivity(Intent(this, Login::class.java))
            } else {
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
            finish()
        })

    }
}