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
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.qol.buddyb.databinding.ActivityLoginBinding
import com.qol.buddyb.ui.theme.BuddyBTheme

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // Web client ID from Firebase Console
                .requestEmail()
                .build()
            val googleSignInClient = GoogleSignIn.getClient(this, gso)
            val signInIntent = googleSignInClient.signInIntent
            val RC_GOOGLE_SIGN_IN = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN)
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {

                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val verifyemail = auth.currentUser?.isEmailVerified
                        val intent = Intent(this, ProfileCreate::class.java)
                        if (verifyemail == true) {
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
                                } else {
                                    val dn = Intent(this, ProfileCreate::class.java)
                                    startActivity(dn)
                                    finish()
                                }
                            }

                        } else {
                            val unverify = Intent(this, VerifyMail::class.java)
                            FirebaseAuth.getInstance().signOut()
                            startActivity(unverify)
                            finish()
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
            finish()
        }
        binding.forgorredirect.setOnClickListener {
            val forgorIntent = Intent(this, Forgor::class.java)
            startActivity(forgorIntent)
            finish()
        }

    }

    private fun startActivityForResult(signInIntent: Intent, rcGoogleSignIn: Intent) {

    }
}