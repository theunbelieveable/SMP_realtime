package com.example.smpapp

import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth


@Suppress("DEPRECATION")
class ForgotEmailActivity : AppCompatActivity() {
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_email)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val backButton: FloatingActionButton = findViewById(R.id.forgot_email_back)
        backButton.setOnClickListener {
            finish()
        }
        val email: EditText = findViewById(R.id.forgot_email_edittext)
        val next: Button = findViewById(R.id.forgot_email_continue)
        next.setOnClickListener {
            email.text.toString()

            FirebaseAuth.getInstance().sendPasswordResetEmail(email.text.toString()).addOnCompleteListener (this){
                task ->
                if(task.isSuccessful){
                   val intent = Intent(this, ForgotPasswordEmailSent::class.java)

                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Could not send Email Verification", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }
}