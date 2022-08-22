package com.example.smpapp

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val payBack: FloatingActionButton = findViewById(R.id.pay_back_icon)
        payBack.setOnClickListener {
            finish()
        }

        val payNext: Button = findViewById(R.id.pay_next_page)
        val name = Firebase.auth.currentUser?.displayName
        var hour = intent.getIntExtra("ticket_time", 10)
        var slot = intent.getStringExtra("slot")
        var location = intent.getStringExtra("location")
        val payCost: TextView = findViewById(R.id.pay_cost_amount)
        val payHolder: TextView = findViewById(R.id.credit_card_holder)
        val payExp: TextView = findViewById(R.id.credit_card_exp)
        val date = Calendar.getInstance()
        date.add(Calendar.HOUR_OF_DAY, hour)
        val formatter = SimpleDateFormat("MM/dd")//or use getDateInstance()
        val formatedDate = formatter.format(date.time)
        val totalSection: LinearLayout = findViewById(R.id.pay_total_section)
        println("--------------==============-----")
        println(formatedDate)
        payHolder.text = name
        payExp.text = formatedDate
        payCost.text = "$" + hour.toString() + ".00"
//        val exp = date.get(Calendar.MONTH)+"/"

        val bottomVanish: LinearLayout = findViewById(R.id.pay_vanish_total_next)
        val textVanish: TextView = findViewById(R.id.pay_vanish_text)
        val cardVanish: ConstraintLayout = findViewById(R.id.pay_vanish_card)
        if (hour == 10) {
            payNext.visibility = View.GONE
            totalSection.visibility = View.GONE
        }
        val addCard: Button = findViewById(R.id.add_card)
        addCard.setOnClickListener {
            if (bottomVanish.isVisible) {
                return@setOnClickListener
            } else {
                bottomVanish.visibility = View.VISIBLE
                textVanish.visibility = View.VISIBLE
                cardVanish.visibility = View.VISIBLE
            }
        }
        payNext.setOnClickListener {
            val db = Firebase.firestore
            //delete the slot from the database

            //realtime database

            val database = Firebase.database("https://smart-park-9a39e-default-rtdb.firebaseio.com/")
            val myRef = database.getReference(location!!)

            myRef.child(slot!!).removeValue().addOnSuccessListener {
                Toast.makeText(
                    this,
                    "on success",
                    Toast.LENGTH_SHORT
                ).show()
            }.addOnFailureListener { e ->
                Toast.makeText(
                    this,
                    "on success" + e.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
//
//
//            //firestore previous stuff
//
//            db.collection("parking_data")
//                .whereEqualTo("location", location)
//                .get()
//                .addOnSuccessListener { documents ->
//                    for (document in documents) {
//                        println("----------------------------------------------")
//                        println("${document.id} => ${document.data["open_slots"]}")
//                        document.reference.update("open_slots",
//                            FieldValue.arrayRemove(slot)
//                        )
//                    }
//                }


            //change page
            val intent = Intent(this, PaymentSuccess::class.java)
            intent.putExtra("location", location)
            intent.putExtra("slot", slot)
            intent.putExtra("ticket_time", hour)
            startActivity(intent)
            finish()
        }
    }
}