package com.qol.buddyb

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.qol.buddyb.databinding.ActivityRegisterBinding
import com.qol.buddyb.databinding.ActivityVerifymailBinding
import com.qol.buddyb.ui.theme.BuddyBTheme

class VerifyMail : ComponentActivity() {

    private lateinit var binding: ActivityVerifymailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifymailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnBacklogin.setOnClickListener{
            val backloginIntent = Intent(this, Login::class.java)
            startActivity(backloginIntent)
        }
    }
}