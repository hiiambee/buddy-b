package com.qol.buddyb

import android.content.Intent
import android.os.Bundle
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
import com.google.firebase.auth.ktx.auth
import com.qol.buddyb.databinding.ActivityMainactivityBinding
import com.qol.buddyb.ui.theme.BuddyBTheme

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainactivityBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        if (user != null) {
            binding.user.setText(user.email)
        } else {
            val backlogin = Intent(this, Login::class.java)
            startActivity(backlogin)
            finish()
        }
        binding.btnSignout.setOnClickListener{
            FirebaseAuth.getInstance().signOut();
            val backlogin = Intent(this, Login::class.java)
            startActivity(backlogin)
            finish()
        }


    }
}