package com.example.smpapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*


@Suppress("DEPRECATION")
class TicketActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val user = Firebase.auth.currentUser
        var hour = intent.getIntExtra("ticket_time", 10)
        var slot = intent.getStringExtra("slot")
        var location = intent.getStringExtra("location")
        var db = Firebase.firestore
        var car_reg: String = ""
        var vehicle_name: String = "Audi A3"
        val ticketDone: Button = findViewById(R.id.ticket_done)
        ticketDone.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        val name = user?.displayName

        val ticketLocation: TextView = findViewById(R.id.ticket_location)
        val ticketVehicle: TextView = findViewById(R.id.ticket_vehicle)
        val ticketUser: TextView = findViewById(R.id.ticket_user)
        val ticketDuration: TextView = findViewById(R.id.ticket_duration)
        val ticketSlot: TextView = findViewById(R.id.ticket_slot)
        ticketSlot.text = "Slot "+slot
        val date = Calendar.getInstance()
        date.add(Calendar.HOUR_OF_DAY, hour)
        val formatter = SimpleDateFormat("dd MMM, yyyy")//or use getDateInstance()
        val formatedDate = formatter.format(date.time)

        ticketDuration.text = hour.toString()+" hours • "+formatedDate
        ticketUser.text = name

        if (location == "sussex"){
            ticketLocation.text = "Parking Lot of Victoria University"
        } else if (location == "quay"){
            ticketLocation.text = "Parking Lot of Circular Quay"
        } else {
            ticketLocation.text = "Parking Lot of Darlilng Harbour"
        }
        println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$")
        println(user?.email)
        db.collection("user_information")
            .whereEqualTo("email", user?.email)
            .get()
            .addOnSuccessListener {
                    documents ->
                for (document in documents){
                    println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
                    println(document.data["car_reg"] as String)

                    car_reg = document.data["car_reg"] as String
                    vehicle_name = document.data["vehicle_name"] as String
                    ticketVehicle.text = "$vehicle_name • $car_reg"
                }
            }
    }
}