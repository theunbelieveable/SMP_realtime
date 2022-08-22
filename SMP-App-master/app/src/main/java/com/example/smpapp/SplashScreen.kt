package com.example.smpapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)

//        val myRef = database.getReference("message")
//
//        myRef.setValue("Hello, World!")
//

//        val database = Firebase.database("https://smart-park-9a39e-default-rtdb.firebaseio.com/")
//        val myRef = database.getReference("sussex")

        //Reads data
//        myRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                val value = dataSnapshot.getValue<HashMap<String, *>>()
//                println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
//                println(value?.size)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Failed to read value
//
//                println("###############################")
//                println(error)
//            }
//        })
//        val slot:String = "A02"
//        myRef.child(slot).removeValue().addOnSuccessListener {
//            Toast.makeText(
//                this,
//                "on success",
//                Toast.LENGTH_SHORT
//            ).show()
//        }.addOnFailureListener { e ->
//            Toast.makeText(
//                this,
//                "on success" + e.message,
//                Toast.LENGTH_SHORT
//            ).show()
//        }


    }
}