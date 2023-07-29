package com.qol.buddyb

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.qol.buddyb.databinding.ActivityBilladdBinding
import com.qol.buddyb.databinding.ActivityExpaddBinding
import com.qol.buddyb.databinding.ActivitySavaddBinding
import com.qol.buddyb.ui.theme.BuddyBTheme
import java.util.Calendar

@Suppress("DEPRECATION")
class ExpAdd : AppCompatActivity() {

    private lateinit var binding: ActivityExpaddBinding
    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore
    private val selectedDate: Calendar = Calendar.getInstance()
    private val selectedTime: Calendar = Calendar.getInstance()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpaddBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnCancelexp.setOnClickListener{
            super.onBackPressed()
        }

        binding.btnAddexp.setOnClickListener {
            val title = binding.titleexp.text.toString()
            val amountString = binding.amountexp.text.toString().trim()
            val datePicker = binding.dateexp
            val timePicker = binding.timeexp
            val userid = FirebaseAuth.getInstance().currentUser!!.uid
            db = FirebaseFirestore.getInstance()

            selectedDate.set(datePicker.year, datePicker.month, datePicker.dayOfMonth)
            selectedTime.set(Calendar.HOUR_OF_DAY, timePicker.hour)
            selectedTime.set(Calendar.MINUTE, timePicker.minute)
            val combinedTimestamp = getCombinedTimestamp(selectedDate, selectedTime)
            val amount = amountString.toDoubleOrNull()

            if (amount == null) {
                Toast.makeText(this, "Invalid amount format.", Toast.LENGTH_SHORT).show()
            } else {
                val expmap = hashMapOf(
                    "title" to title,
                    "amount" to amount,
                    "date" to combinedTimestamp
                )

                db.collection("Database").document(userid)
                    .collection("expense")
                    .document()
                    .set(expmap)
                    .addOnSuccessListener {
                        super.onBackPressed()
                        Toast.makeText(this, "Added successfully!", Toast.LENGTH_SHORT)
                            .show()
                    }.addOnFailureListener {
                        Toast.makeText(
                            this,
                            "An error occured. Please check your internet connection.",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
            }








        }


    }

    private fun getCombinedTimestamp(date: Calendar, time: Calendar): Timestamp {
        val combinedDateTime = Calendar.getInstance()
        combinedDateTime.set(Calendar.YEAR, date.get(Calendar.YEAR))
        combinedDateTime.set(Calendar.MONTH, date.get(Calendar.MONTH))
        combinedDateTime.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH))
        combinedDateTime.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY))
        combinedDateTime.set(Calendar.MINUTE, time.get(Calendar.MINUTE))
        combinedDateTime.set(Calendar.SECOND, 0)
        combinedDateTime.set(Calendar.MILLISECOND, 0)

        return Timestamp(combinedDateTime.time)
    }
}