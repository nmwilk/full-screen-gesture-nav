package com.nmwilkinson.fullscreengesturenav

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.nmwilkinson.fullscreengesturenav.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.transientButton.setOnClickListener { startActivity(Intent(this, TransientBarsActivity::class.java)) }
        binding.opaqueButton.setOnClickListener { startActivity(Intent(this, OpaqueBarsActivity::class.java)) }
    }
}