package com.example.smpapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager

@Suppress("DEPRECATION")
class PaymentSuccess : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_success)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val loc = intent.getStringExtra("location")
        val slot = intent.getStringExtra("slot")
        val ticket_time = intent.getIntExtra("ticket_time", 10)
        Handler().postDelayed({
            val intent = Intent(this, TicketActivity::class.java)
            intent.putExtra("location", loc)
            intent.putExtra("slot", slot)
            intent.putExtra("ticket_time", ticket_time)
            startActivity(intent)
            finish()
        }, 500)
    }
}