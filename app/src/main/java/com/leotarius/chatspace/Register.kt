package com.leotarius.chatspace

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.leotarius.chatspace.models.User
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.login.error
import kotlinx.android.synthetic.main.login.register
import kotlinx.android.synthetic.main.register.*
import kotlin.error

class Register: AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val TAG = "RegisterActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        auth = FirebaseAuth.getInstance()

        register.setOnClickListener {
            val email_text = email.text.toString()
            val passwrd = password.text.toString()
            val full_name = name.text.toString()

            if(UtilFunctions.isValidEmail(email_text) && passwrd.length>=8 && full_name.length!=0){
                error.visibility = View.GONE
                registerUser(email_text, passwrd, full_name);
            } else if(!UtilFunctions.isValidEmail(email_text)){
                error.setText(getString(R.string.email_invalid_error))
                error.visibility = View.VISIBLE
            } else if(passwrd.length<8){
                error.setText(getString(R.string.password_invalid_error))
                error.visibility = View.VISIBLE
            } else if(full_name.length==0){
                error.setText(getString(R.string.name_invalid_error))
                error.visibility = View.VISIBLE
            }
        }
    }

    private fun registerUser(email: String, password: String, name: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    Log.d(TAG, "registerUser: registration successful");
                    addUserToDatabase(email, name);

                    // redirect to main activity and finish this so user cant press back button
                    startActivity(Intent(baseContext, MainActivity::class.java))
                    finish()

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed: " +
                            task.exception.toString().substring(task.exception.toString().lastIndexOf(":") + 1),
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(email: String, name: String){
        Log.d(TAG, "addUserToDatabase: adding user to database...")
        val authUser = auth.currentUser

        // making a new user to save to database
        val user = User(authUser?.uid.toString()?:"0", name, email)

        // saving the user to the database
        val dbRef = FirebaseDatabase.getInstance().reference.child("users").child(user.uid)
        dbRef.setValue(user)
    }
}