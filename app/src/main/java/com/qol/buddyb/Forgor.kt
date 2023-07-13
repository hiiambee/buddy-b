package com.qol.buddyb

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseAuth
import com.qol.buddyb.databinding.ActivityForgorBinding
import com.qol.buddyb.ui.theme.BuddyBTheme

class Forgor : ComponentActivity() {

    private lateinit var binding: ActivityForgorBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnForgor.setOnClickListener {
            val email = binding.forgorEmail.text.toString()

            if (email.isNotEmpty()) {
                auth.sendPasswordResetEmail(email)
                    .addOnSuccessListener {
                        val redirect2 = Intent(this, Forgor2::class.java)
                        startActivity(redirect2)
                    }
                    .addOnFailureListener {
                        val redirect3 = Intent(this, Forgor2::class.java)
                        startActivity(redirect3)
                    }
            } else {
                Toast.makeText(this, "Please input your login credentials.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}