package com.example.smpapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton


@Suppress("DEPRECATION")
class ParkingDetailSussex : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking_detail_sussex)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val quayBackIcon: FloatingActionButton = findViewById(R.id.sussex_back_button)
        val quayLocationIcon: FloatingActionButton = findViewById(R.id.sussex_location_icon)
        val mBottomSheetLayout: LinearLayout = findViewById(R.id.sussex_bottom_sheet_layout2)
        val sheetBehavior = BottomSheetBehavior.from(mBottomSheetLayout)
        val direction: Button = findViewById(R.id.sussex_direction)
        val slots_available = intent.getIntExtra("slots_available", 5)

        direction.setOnClickListener {
            val intent = Intent(this, SelectSlot::class.java)
            intent.putExtra("location", "sussex")
            startActivity(intent)
        }
        sheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                val isVisible = when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> false
                    BottomSheetBehavior.STATE_COLLAPSED -> true
                    else -> false
                }
                if (isVisible){

                    quayBackIcon.show()
                    quayLocationIcon.show()
                }
                else {
                    val tbutton: Button = findViewById(R.id.bottom_sheet_sussex_slots)
                    tbutton.text = slots_available.toString()+" slots available"

                    quayBackIcon.hide()
                    quayLocationIcon.hide()
                }
            }
        })
        quayBackIcon.setOnClickListener {
            finish()
        }
    }
}