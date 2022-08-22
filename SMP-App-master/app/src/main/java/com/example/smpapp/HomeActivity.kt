package com.example.smpapp

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.*
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ModalBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.modal_bottom_sheet_content, container, true)

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}

@Suppress("DEPRECATION")
class HomeActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    var available_slots_sussex: Int = 3
    var available_slots_darling: Int = 6
    var available_slots_quay: Int = 9
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
        setContentView(R.layout.activity_home)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        auth = Firebase.auth
        auth.currentUser?.displayName
//        val db = Firebase.firestore
//        db.collection("parking_data")
//            .get()
//            .addOnSuccessListener {
//                    documents ->
//                for (document in documents){
//                   val item = document.data["location"] as String
//                    val t = document.data["open_slots"] as ArrayList<String>
////                    document.reference.addSnapshotListener{
////                        snapshot, e ->
////                        if (snapshot != null && snapshot.exists()){
////                            println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&")
////                            println(snapshot.data)
////                        }
////                    }
//                    if (item.equals("sussex")){
//
//                        available_slots_sussex = t.size
//                    }
//                    else if(item.equals("quay")){
//                        available_slots_quay = t.size
//                    } else if(item.equals("darling")){
//                        available_slots_darling = t.size
//                    }
//                }
//            }

        val database = Firebase.database("https://smart-park-9a39e-default-rtdb.firebaseio.com/")
        var myRef = database.getReference("sussex")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                available_slots_sussex = dataSnapshot.getValue<HashMap<String, *>>()!!.size
            }
            override fun onCancelled(error: DatabaseError) {}
        })
        myRef = database.getReference("darling")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                available_slots_darling = dataSnapshot.getValue<HashMap<String, *>>()!!.size
            }
            override fun onCancelled(error: DatabaseError) {}
        })
        myRef = database.getReference("quay")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                available_slots_quay = dataSnapshot.getValue<HashMap<String, *>>()!!.size
            }
            override fun onCancelled(error: DatabaseError) {}
        })

        println("------------------------------")
        println(auth.currentUser?.displayName)
        val mBottomSheetLayout: LinearLayout = findViewById(R.id.bottom_sheet_layout2)
        val searchLayout: LinearLayout = findViewById(R.id.bottom_sheet_header)
        val drawer_icon: FloatingActionButton = findViewById(R.id.drawer_icon)
        val notification_icon : FloatingActionButton = findViewById(R.id.notification_icon)
        val car_icon: FloatingActionButton = findViewById(R.id.car_icon)
        val sheetBehavior = BottomSheetBehavior.from(mBottomSheetLayout)
        val searchSection: TextInputLayout = findViewById(R.id.search_section)
        val itemScroll: ScrollView = findViewById(R.id.item_scroll)
//        val header_image: ImageView = findViewById(R.id.bottom_sheet_arrow)
        val search_location: EditText = findViewById(R.id.search_location)
        val sussex_location_click: LinearLayout = findViewById(R.id.sussex_location_item)
        val quay_location_click: LinearLayout = findViewById(R.id.circular_location_item)
        val darling_location_click: LinearLayout = findViewById(R.id.darling_location_item)
        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
//        drawer.openDrawer(Gravity.LEFT)
        drawer_icon.setOnClickListener {
            println(drawer.isDrawerOpen(Gravity.LEFT))
            drawer.openDrawer(Gravity.LEFT)
            if (drawer.isDrawerOpen(Gravity.LEFT)) {
                drawer.closeDrawer(Gravity.LEFT)
            } else {
                drawer.openDrawer(Gravity.LEFT)

                val drawerNavButton: Button = findViewById(R.id.header_nav_drawer_icon)
                val drawerUsername: TextView = findViewById(R.id.drawer_username)
                drawerUsername.text = auth.currentUser?.displayName
                val drawerNearbyButton: TextView = findViewById(R.id.drawer_nearby_button)
                val drawerPaymentButton: TextView = findViewById(R.id.drawer_payment_button)
                val drawerLogoutButton: ImageButton = findViewById(R.id.drawer_logout_button)
                drawerLogoutButton.setOnClickListener {
                    auth.signOut()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                drawerNavButton.setOnClickListener {
                    drawer.closeDrawer(Gravity.LEFT)
                }
                drawerNearbyButton.setOnClickListener {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
                    drawer.closeDrawer(Gravity.LEFT)
                }
                drawerPaymentButton.setOnClickListener{
                    val intent = Intent(this, PaymentActivity::class.java)
                    startActivity(intent)
                }
            }
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
                    val param = searchLayout.layoutParams as ViewGroup.MarginLayoutParams
                    param.setMargins(0, 0, 0, 0)
                    searchLayout.layoutParams = param

                    val param2 = searchSection.layoutParams as ViewGroup.MarginLayoutParams
                    param2.setMargins(16, 50, 16, 20)
                    searchSection.layoutParams = param2

                    drawer_icon.show()
                    notification_icon.show()
                    car_icon.show()
                }
                else {
                    val param2 = searchSection.layoutParams as ViewGroup.MarginLayoutParams
                    param2.setMargins(16, 100, 16, 100)
                    searchSection.layoutParams = param2

                    val param = searchLayout.layoutParams as ViewGroup.MarginLayoutParams
                    param.setMargins(0, 150, 0, 0)
                    searchLayout.layoutParams = param

                    itemScroll.scrollTo(0,0)
                    drawer_icon.hide()
                    notification_icon.hide()
                    car_icon.hide()

                    val quay_slot: TextView = findViewById(R.id.available_slots_quay)

                    val darling_slot: TextView = findViewById(R.id.available_slots_darling)

                    val sussex_slot: TextView = findViewById(R.id.available_slots_sussex)
                    quay_slot.text = available_slots_quay.toString()+" slots available"
                    darling_slot.text = available_slots_darling.toString()+" slots available"
                    sussex_slot.text = available_slots_sussex.toString()+" slots available"
                }
            }
        })
        search_location.setOnFocusChangeListener(
            (OnFocusChangeListener { _, hasFocus ->
                if(hasFocus) {
                    if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
                    }
//                    else {
//                        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
//                        drawer_icon.show()
//                        notification_icon.show()
//                        car_icon.show()
//                    }
                }
            })
        )
        quay_location_click.setOnClickListener {
            val intent = Intent(this, ParkingDetailCircularQuay::class.java)
            intent.putExtra("slots_available", available_slots_quay)
            startActivity(intent)

        }
        darling_location_click.setOnClickListener {
            val intent = Intent(this, ParkingDetailDarling::class.java)
            intent.putExtra("slots_available", available_slots_darling)
            startActivity(intent)
        }
        sussex_location_click.setOnClickListener {
            val intent = Intent(this, ParkingDetailSussex::class.java)
            intent.putExtra("slots_available", available_slots_sussex)
            startActivity(intent)
        }



//        val modalBottomSheet = ModalBottomSheet()
//        modalBottomSheet.show(supportFragmentManager, ModalBottomSheet.TAG)
//        println("++++++++++++++++++++++++++++++++++++++++++++")
//        println("Inside homeactivity!")
//        println(FirebaseAuth.getInstance().currentUser?.email)
    }
}
