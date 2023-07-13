package com.qol.buddyb

import android.app.Activity
import android.content.ContentProviderClient
import android.content.Intent
import android.os.Bundle
import android.graphics.drawable.ColorDrawable
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider
import com.qol.buddyb.databinding.ActivityLoginBinding
import com.qol.buddyb.ui.theme.BuddyBTheme

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {

                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val verifyemail = auth.currentUser?.isEmailVerified
                        val intent = Intent(this, MainActivity::class.java)
                        if (verifyemail == true) {
                            auth.currentUser
                            startActivity(intent)
                        } else {
                            val unverify = Intent(this, VerifyMail::class.java)
                            startActivity(unverify)
                        }
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please input your login credentials.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        binding.signupredirect.setOnClickListener {
            val signupIntent = Intent(this, Register::class.java)
            startActivity(signupIntent)
        }
        binding.forgorredirect.setOnClickListener {
            val forgorIntent = Intent(this, Forgor::class.java)
            startActivity(forgorIntent)
        }

    }
}