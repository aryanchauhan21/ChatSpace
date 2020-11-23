package com.leotarius.chatspace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

class MainActivity : AppCompatActivity() {

    lateinit var viewAdapter: ChatAdapter
    companion object{
        var dbUser:User? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val user = FirebaseAuth.getInstance().currentUser
        val dbreference = user?.uid?.let { FirebaseDatabase.getInstance().reference.child(it) }

        dbreference?.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dbUser = snapshot.getValue(User::class.java)
                dbreference.removeEventListener(this)
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        val reference = FirebaseDatabase.getInstance().reference

        send.setOnClickListener {
            if(dbUser!=null){
                val text = message.text.toString()
                val chatMessage = Message(dbUser?.uid, text, dbUser?.name)
                if(text.isNotEmpty()){
                    reference.child("chat").push().setValue(chatMessage)
                    message.setText("")
                }
            }
        }

        val layoutManager = LinearLayoutManager(this)
        val mList = ArrayList<Message>()
        mList.add(Message(user?.uid,"hey man", "name"))
        mList.add(Message("4","hey man2", "name"))
        mList.add(Message("2","hey man3", "name"))
        mList.add(Message(user?.uid,"hey man4", "name"))
        mList.add(Message(user?.uid,"hey man4", "name"))
        mList.add(Message(user?.uid,"hey man4", "name"))
        mList.add(Message(user?.uid,"hey man4", "name"))
        mList.add(Message(user?.uid,"hey man4", "name"))
        viewAdapter = ChatAdapter(this, mList)

        val recyclerView:RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = viewAdapter

    }

}