package com.example.smpapp

import android.R.attr.button
import android.content.Intent
import android.graphics.Color.green
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@Suppress("DEPRECATION")


class SelectSlot : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_slot)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val loc: String? = intent.getStringExtra("location")
//        val loc: String = "sussex"
        var selSlot: String = ""

        //firestore previous


//        db.collection("parking_data")
//            .whereEqualTo("location", loc)
//            .get()
//            .addOnSuccessListener { documents ->
//                for (document in documents) {
//                    println("----------------------------------------------")
//                    println("${document.id} => ${document.data["open_slots"]}")
//                    val t = document.data["open_slots"] as ArrayList<String>
//                    println(t.size)
//                    val button = arrayOfNulls<Button>(t.size)
//                    for (i in button.indices) {
//                        println(t[i])
//                        val id = resources.getIdentifier("slot_${t[i]}", "id", packageName)
//
//                        button[i] = findViewById<View>(id) as Button
//                    }
//                    for (butt in button) {
//                        val t = butt?.text.toString().subSequence(1, butt?.text.toString().length)
//                        val num = t.toString().toInt()
//                        if (num < 8 || (num in 11..13)) {
//
//                            butt?.setBackgroundResource(R.drawable.right_no_border_green)
//                        } else {
//                            butt?.setBackgroundResource(R.drawable.left_no_border_green)
//                        }
//                        butt?.setOnClickListener {
//                            //first turn all of the buttons into normal green
//                            //then select this one
//                            println("I clicked")
//                            println(selSlot)
//                            for (all_butt in button){
//                                if(butt.text.equals(all_butt?.text)){
//                                    if (butt.background.constantState != getDrawable(R.drawable.selected_slot_drawable)?.constantState) {
//                                        butt?.setBackgroundResource(R.drawable.selected_slot_drawable)
//                                        continue
//                                    }
//                                }
//                                val t = all_butt?.text.toString().subSequence(1, all_butt?.text.toString().length)
//                                println(t)
//                                val num = t.toString().toInt()
//                                println(num)
//                                if (num < 8 || (num in 11..13)) {
//
//                                    all_butt?.setBackgroundResource(R.drawable.right_no_border_green)
//                                } else {
//                                    all_butt?.setBackgroundResource(R.drawable.left_no_border_green)
//                                }
//                            }
//                            selSlot = if(butt.background.constantState == getDrawable(R.drawable.selected_slot_drawable)?.constantState){
//
//                                butt?.text.toString()
//                            } else {
//                                ""
//                            }
//                            println(selSlot)
//
//                        }
//                    }
//                }
//            }
//            .addOnFailureListener { exception ->
//                println("Error getting documents: ${exception.toString()}")
//            }


        //realtime db
        val database = Firebase.database("https://smart-park-9a39e-default-rtdb.firebaseio.com/")
        val myRef = database.getReference(loc!!)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue<HashMap<String, *>>()
                println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
                println(value!!.keys)
                val t = ArrayList(value!!.keys)

                val button = arrayOfNulls<Button>(value!!.size)
                for (i in 1..20){
                    var temp = ""
                    if(i<10) {
                        temp += "0"
                    }
                    temp+=i.toString()
                    println("=====================")
                    println(temp)
                    val id = resources.getIdentifier("slot_A${temp}", "id", packageName)
                    val b = findViewById<Button>(id) as Button
                    if (i < 8 || (i in 11..13)) {

                        b?.setBackgroundResource(R.drawable.button_border)
                    } else {
                        b?.setBackgroundResource(R.drawable.left_no_border)
                    }
                }
                for (i in button.indices) {
                    println(t[i])
                    val id = resources.getIdentifier("slot_${t[i]}", "id", packageName)

                    button[i] = findViewById<View>(id) as Button
                }
                for (butt in button) {
                    val t = butt?.text.toString().subSequence(1, butt?.text.toString().length)
                    val num = t.toString().toInt()
                    if (num < 8 || (num in 11..13)) {

                        butt?.setBackgroundResource(R.drawable.right_no_border_green)
                    } else {
                        butt?.setBackgroundResource(R.drawable.left_no_border_green)
                    }
                    butt?.setOnClickListener {
                        //first turn all of the buttons into normal green
                        //then select this one
                        println("I clicked")
                        println(selSlot)
                        for (all_butt in button) {
                            if (butt.text.equals(all_butt?.text)) {
                                if (butt.background.constantState != getDrawable(R.drawable.selected_slot_drawable)?.constantState) {
                                    butt?.setBackgroundResource(R.drawable.selected_slot_drawable)
                                    continue
                                }
                            }
                            val t = all_butt?.text.toString()
                                .subSequence(1, all_butt?.text.toString().length)
                            println(t)
                            val num = t.toString().toInt()
                            println(num)
                            if (num < 8 || (num in 11..13)) {

                                all_butt?.setBackgroundResource(R.drawable.right_no_border_green)
                            } else {
                                all_butt?.setBackgroundResource(R.drawable.left_no_border_green)
                            }
                        }
                        selSlot =
                            if (butt.background.constantState == getDrawable(R.drawable.selected_slot_drawable)?.constantState) {

                                butt?.text.toString()
                            } else {
                                ""
                            }
                        println(selSlot)

                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {}
        })


        val backButton: FloatingActionButton = findViewById(R.id.slot_back_icon)
        backButton.setOnClickListener {
            finish()
        }

        val nextButton: Button = findViewById(R.id.slot_continue)
        nextButton.setOnClickListener {
            if (selSlot.equals("")) {
                Toast.makeText(this, "Please select a valid slot", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this, TimerActivity::class.java)
            intent.putExtra("location", loc)
            intent.putExtra("slot", selSlot)
            startActivity(intent)
        }

//        val A01: Button = findViewById(R.id.slot_A01)
//        A01.setOnClickListener {
//            println("---------------------------------------------")
//            println(A01.background.current)
//            println(A01.background)
//            println(A01.background.constantState)
//            A01.setBackgroundResource(R.drawable.selected_slot_drawable)
//            if(A01.background.constantState==getDrawable(R.drawable.selected_slot_drawable)?.constantState){
//                A01.setBackgroundResource(R.drawable.button_border)
//                println(1)
//            }
//            else{
//                println(2)
//                A01.setBackgroundResource(R.drawable.selected_slot_drawable)
//            }
//        }
//        val button = arrayOfNulls<Button>(20)
//
//        for (i in 1..20) {
//            println(i)
//            if (i<10) {
//                val id = resources.getIdentifier("slot_A0$i", "id", packageName)
//
//                button[i-1] = findViewById<View>(id) as Button
//            }
//            else {
//                val id = resources.getIdentifier("slot_A$i", "id", packageName)
//
//                button[i-1] = findViewById<View>(id) as Button
//            }
//        }
//        for(x in button){
//            x!!.setOnClickListener {
//                if (x!!.background.constantState == getDrawable(R.drawable.selected_slot_drawable)?.constantState) {
//                    x.setBackgroundResource(R.drawable.button_border)
//                    println(1)
//                } else {
//                    println(2)
//                    x.setBackgroundResource(R.drawable.selected_slot_drawable)
//                }
//            }
//        }
    }


}