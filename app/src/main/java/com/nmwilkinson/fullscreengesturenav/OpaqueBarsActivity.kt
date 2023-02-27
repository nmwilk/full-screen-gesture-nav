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

class OpaqueBarsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExcludeBinding

    private val types = WindowInsetsCompat.Type.systemBars()
        .or(WindowInsetsCompat.Type.systemGestures())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExcludeBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)

        // including this causes swipe back gestures to work
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_SWIPE

        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { view, windowInsets ->

            autohideBars(windowInsets, windowInsetsController)

            ViewCompat.onApplyWindowInsets(view, windowInsets)
            val insets = windowInsets.getInsets(types)
            logcat { "insets changed $insets" }
            binding.excludeGesturesLayout.setWindowInsets(insets)

            ViewCompat.onApplyWindowInsets(view, windowInsets)
        }
    }

    private fun autohideBars(
        windowInsets: WindowInsetsCompat,
        windowInsetsController: WindowInsetsControllerCompat
    ) {
        if (windowInsets.isVisible(WindowInsetsCompat.Type.systemGestures())
            || windowInsets.isVisible(WindowInsetsCompat.Type.statusBars())
        ) {
            binding.root.postDelayed({
                // Hide both the status bar and the navigation bar.
                windowInsetsController.hide(types)
            }, 2000)
        }
    }

    override fun onResume() {
        super.onResume()

        WindowCompat.getInsetsController(window, window.decorView).hide(types)
    }
}