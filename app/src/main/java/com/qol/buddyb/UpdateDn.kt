package com.qol.buddyb

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.qol.buddyb.databinding.ActivityProfilecreateBinding
import com.qol.buddyb.databinding.ActivityUpdatednBinding
import com.qol.buddyb.ui.theme.BuddyBTheme

class UpdateDn : AppCompatActivity() {

    private lateinit var binding: ActivityUpdatednBinding
    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdatednBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnUpdateproceed.setOnClickListener{
            val dn = binding.updatedisplayname.text.toString().trim()

            if (dn.isNotEmpty()) {
                val usermap = hashMapOf(
                    "displayname" to dn
                ).toMap()

                val userid = FirebaseAuth.getInstance().currentUser!!.uid

                db.collection("displayname").document(userid).update(usermap)
                    .addOnSuccessListener {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    .addOnFailureListener{
                        Toast.makeText(this, "An error occurred. Please check your internet connection.", Toast.LENGTH_SHORT)
                    }
            } else {
                Toast.makeText(this, "All fields cannot be empty.", Toast.LENGTH_SHORT).show()
            }

        }


    }
}