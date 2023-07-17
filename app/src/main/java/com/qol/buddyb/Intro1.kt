package com.qol.buddyb

import android.content.Intent
import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.qol.buddyb.databinding.ActivityIntro1Binding

class Intro1 : AppCompatActivity() {

    private lateinit var binding: ActivityIntro1Binding
    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore

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
            val uid = FirebaseAuth.getInstance().currentUser!!.uid
            val ref = db.collection("displayname").document(uid)
            ref.get().addOnSuccessListener {
                if (it != null) {
                    val disna = it.data?.get("displayname")?.toString()
                    if (disna != null) {
                        val skipdn = Intent(this, MainActivity::class.java)
                        startActivity(skipdn)
                        finish()
                    } else {
                        val dn = Intent(this, ProfileCreate::class.java)
                        startActivity(dn)
                        finish()
                    }

                }
            }
        }
    }
}