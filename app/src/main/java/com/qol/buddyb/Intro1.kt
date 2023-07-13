package com.qol.buddyb

import android.content.Intent
import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.qol.buddyb.databinding.ActivityIntro1Binding

class Intro1 : AppCompatActivity() {

    private lateinit var binding: ActivityIntro1Binding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntro1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.next1.setOnClickListener {
            val intro2Intent = Intent(this, Intro2::class.java)
            startActivity(intro2Intent)
            finish()
        }

        binding.skiplogin1.setOnClickListener {
            val login1Intent = Intent(this, Login::class.java)
            startActivity(login1Intent)
            finish()
        }
    }
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val skiplogin = Intent(this, MainActivity::class.java)
            startActivity(skiplogin)
            finish()
        }
    }
}