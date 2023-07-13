package com.qol.buddyb

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
import com.qol.buddyb.databinding.ActivityForgorBinding
import com.qol.buddyb.ui.theme.BuddyBTheme

class Forgor : ComponentActivity() {

    private lateinit var binding: ActivityForgorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgorBinding.inflate(layoutInflater)
        setContentView(binding.root)
            }
        }