package com.example.smpapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

@Suppress("DEPRECATION")
class TimerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        var ticket_time: Int = 4
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        println("----------------")
        println(intent.getStringExtra("location"))
        println(intent.getStringExtra("slot"))
        val time: TextView = findViewById(R.id.timer_time)
        val incTime: Button = findViewById(R.id.increase_time)
        val yes: Button = findViewById(R.id.timer_yes)
        val no: Button = findViewById(R.id.timer_no)
        val cross: FloatingActionButton = findViewById(R.id.timer_cross_button)
        val timerImage: ImageView = findViewById(R.id.timer_image)


        yes.setOnClickListener {
            val t = Intent(this, PaymentActivity::class.java)
            t.putExtra("location",intent.getStringExtra("location") )
            t.putExtra("slot", intent.getStringExtra("slot"))
            t.putExtra("ticket_time", ticket_time)
            startActivity(t)

        }
        no.setOnClickListener {
            finish()
        }
        cross.setOnClickListener {
            finish()
        }
        val descTime: Button = findViewById(R.id.decrease_time)

        incTime.setOnClickListener {
            val t = time.text.toString().subSequence(0, 2)
            val tt = t.toString().toInt()
            if (tt<=3){
                val nextTime = "0"+(tt+1).toString()+" : 00 : 00"
                time.text = nextTime
                ticket_time = tt + 1
                val id = resources.getIdentifier("quarter_$ticket_time", "drawable", packageName)

                timerImage.setImageResource(id)
            }
            else{
                return@setOnClickListener
            }
        }

        descTime.setOnClickListener {
            val t = time.text.toString().subSequence(0, 2)
            val tt = t.toString().toInt()
            if (tt>1){
                val nextTime = "0"+(tt-1).toString()+" : 00 : 00"
                time.text = nextTime
                ticket_time = tt - 1

                val id = resources.getIdentifier("quarter_$ticket_time", "drawable", packageName)

                timerImage.setImageResource(id)
            }
            else{
                return@setOnClickListener
            }
        }

    }
}