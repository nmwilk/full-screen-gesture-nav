package com.nmwilkinson.fullscreengesturenav

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.nmwilkinson.fullscreengesturenav.databinding.ActivityExcludeBinding
import logcat.logcat

class TransientBarsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExcludeBinding

    private val types = WindowInsetsCompat.Type.systemBars()
        .or(WindowInsetsCompat.Type.systemGestures())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExcludeBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)

        // including this prevents swipe back gestures from working
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { view, windowInsets ->
            ViewCompat.onApplyWindowInsets(view, windowInsets)
            val insets = windowInsets.getInsets(types)
            logcat { "insets changed $insets" }
            binding.excludeGesturesLayout.setWindowInsets(insets)
            windowInsets
        }
    }

    override fun onResume() {
        super.onResume()

        WindowCompat.getInsetsController(window, window.decorView).hide(types)
    }
}