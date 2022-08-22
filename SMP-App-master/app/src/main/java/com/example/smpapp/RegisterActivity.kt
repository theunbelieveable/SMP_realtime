package com.example.smpapp

import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Suppress("DEPRECATION")
class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
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
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }
        setContentView(R.layout.activity_register)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        auth = Firebase.auth
        val db = Firebase.firestore
        val email: EditText = findViewById(R.id.email)
        val password: EditText = findViewById(R.id.password)
        val name: EditText = findViewById(R.id.name)
        val carReg: EditText = findViewById(R.id.car_reg)
        val vehicleName: EditText = findViewById(R.id.vehicle_name)
        val agree: CheckBox = findViewById(R.id.check)

        findViewById<Button>(R.id.login).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.register).setOnClickListener {
            if( !agree.isChecked){
                Toast.makeText(this, "Please Read and Agree T&C to Use this App", Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            }
            else if (email.text.toString() == "") {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            } else if (password.text.toString() == "") {
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            } else if (carReg.text.toString() == "") {
                Toast.makeText(this, "Please enter your car registration", Toast.LENGTH_SHORT)
                    .show();
                return@setOnClickListener
            } else if (name.text.toString() == "") {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
//                        Log.d("Successfully Created user", "createUserWithEmail:success")
                        val user = auth.currentUser

                        Toast.makeText(this, "Registered succesfully", Toast.LENGTH_SHORT).show();
                        val profileUpdates = userProfileChangeRequest {
                            displayName = name.text.toString()

                        }
                        user!!.updateProfile(profileUpdates)
                            .addOnCompleteListener{ task ->
                                if (task.isSuccessful) {
                                    println("GOTO NEXT PAGE. EVERYTHING IS SET")
                                    val ref = db.collection("user_information").document()
                                    val data = hashMapOf(
                                        "email" to email.text.toString().lowercase(),
                                        "car_reg" to carReg.text.toString(),
                                        "vehicle_name" to vehicleName.text.toString(),
                                    )
                                    ref.set(data)
                                    val intent = Intent(this, SignUpSuccessActivity::class.java)
                                    startActivity(intent)
                                } else {
                                    Toast.makeText(
                                        baseContext,
                                        "Registration failed. ${task.exception.toString()}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        // If sign in fails, display a message to the user.
//                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        println("-------------------------------------------------------------------")
                        println(task.exception.toString())
                        Toast.makeText(
                            baseContext, "Registration failed. ${task.exception.toString()}",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
        }
    }
}