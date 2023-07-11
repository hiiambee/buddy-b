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
import com.qol.buddyb.databinding.ActivityIntro3Binding
import com.qol.buddyb.ui.theme.BuddyBTheme

class Intro3 : AppCompatActivity() {

    private lateinit var binding: ActivityIntro3Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntro3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.next3.setOnClickListener {
            val intro2Intent = Intent(this, Login::class.java)
            startActivity(intro2Intent)
        }

        binding.skiplogin3.setOnClickListener {
            val login1Intent = Intent(this, Login::class.java)
            startActivity(login1Intent)
        }
    }
}