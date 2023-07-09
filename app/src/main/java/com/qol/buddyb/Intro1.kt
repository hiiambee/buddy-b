package com.qol.buddyb

import android.content.Intent
import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.qol.buddyb.databinding.ActivityIntro1Binding
import com.qol.buddyb.databinding.ActivityLoginBinding

class Intro1 : AppCompatActivity() {

    private lateinit var binding: ActivityIntro1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntro1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.next1.setOnClickListener {
            val intro2Intent = Intent(this, Intro2::class.java)
            startActivity(intro2Intent)
        }

        binding.skiplogin1.setOnClickListener {
            val login1Intent = Intent(this, Login::class.java)
            startActivity(login1Intent)
        }
    }
}