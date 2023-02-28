package com.nmwilkinson.fullscreengesturenav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowInsets
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.nmwilkinson.fullscreengesturenav.databinding.ActivityExcludeBinding

class OpaqueBarsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExcludeBinding

    private val types =
        WindowInsetsCompat.Type.systemBars().or(WindowInsetsCompat.Type.systemGestures())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExcludeBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)

        // including this causes swipe back gestures to work
        // This avoid transient bars. Allowing navigation gestures
        // including back gesture, swipe up gesture to go back to app launcher
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_SWIPE

        // Add a listener to update the behavior of the toggle fullscreen button when
        // the system bars are hidden or revealed.
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { view, windowInsets ->

            autohideBars(windowInsets, windowInsetsController)

            ViewCompat.onApplyWindowInsets(view, windowInsets)
        }
    }

    private fun autohideBars(
        windowInsets: WindowInsetsCompat,
        windowInsetsController: WindowInsetsControllerCompat
    ) {
        if (windowInsets.isVisible(WindowInsetsCompat.Type.navigationBars())
            || windowInsets.isVisible(
                WindowInsetsCompat.Type.statusBars()
            )
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