package com.leotarius.chatspace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.leotarius.chatspace.models.Message
import com.leotarius.chatspace.models.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_toolbar2.*

class MainActivity : AppCompatActivity() {

    lateinit var viewAdapter: ChatAdapter
    companion object{
        var dbUser:User? = null
    }

    private val TAG = "MainActivity"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val user = FirebaseAuth.getInstance().currentUser
        val dbreference = FirebaseDatabase.getInstance().reference.child("users").child(user?.uid!!)

        dbreference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dbUser = snapshot.getValue(User::class.java)
                Log.d(TAG, "onDataChange: ${dbUser.toString()}")
                if (dbUser != null) Log.d(TAG, "onDataChange: not null")
                dbreference.removeEventListener(this)
                Log.d(TAG, "onDataChange: got user from database")
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        val reference = FirebaseDatabase.getInstance().reference

        send.setOnClickListener {
            Log.d(TAG, "onCreate: click")
            if(dbUser!=null){
                Log.d(TAG, "onCreate: clickedin")
                val text = message.text.toString()
                val chatMessage = Message(dbUser?.uid, text, dbUser?.name)
                if(text.isNotEmpty()){
                    reference.child("chat").push().setValue(chatMessage)
                    message.setText("")
                }
            }
        }

        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, Login::class.java))
            finish()
        }

        val layoutManager = LinearLayoutManager(this)
        val mList = ArrayList<Message>()
        mList.add(Message("1","Hey man", "Aryan Bharadwaj"))
        mList.add(Message(user?.uid,"Hey mate", "Aryan Chauhan"))
        mList.add(Message("1","How you doing?", "Aryan Bharadwaj"))
        mList.add(Message(user?.uid,"I'm good buddy.", "Aryan Chauhan"))
        mList.add(Message(user?.uid,"You say", "Aryan Chauhan"))
        viewAdapter = ChatAdapter(this, mList)

        val recyclerView:RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = viewAdapter

    }

}