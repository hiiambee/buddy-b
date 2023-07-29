package com.qol.buddyb

import android.content.Intent
import android.os.Bundle
import android.os.Handler
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
import com.qol.buddyb.databinding.ActivityChangepassBinding
import com.qol.buddyb.databinding.ActivityForgor2Binding
import com.qol.buddyb.ui.theme.BuddyBTheme

class ChangePass : AppCompatActivity() {

    private lateinit var binding: ActivityChangepassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangepassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackhome.setOnClickListener{
            val backlogin2Intent = Intent(this, MainActivity::class.java)
            startActivity(backlogin2Intent)
            finish()
        }

    }
}