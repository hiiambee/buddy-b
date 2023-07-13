package com.qol.buddyb

import android.content.Intent
import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.qol.buddyb.databinding.ActivityIntro2Binding
class Intro2 : AppCompatActivity() {

    private lateinit var binding: ActivityIntro2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntro2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.next2.setOnClickListener {
            val intro2Intent = Intent(this, Intro3::class.java)
            startActivity(intro2Intent)
            finish()
        }

        binding.skiplogin2.setOnClickListener {
            val login1Intent = Intent(this, Login::class.java)
            startActivity(login1Intent)
            finish()
        }
    }
}