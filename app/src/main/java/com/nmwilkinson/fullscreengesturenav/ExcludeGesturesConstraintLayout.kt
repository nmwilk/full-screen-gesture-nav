package com.nmwilkinson.fullscreengesturenav

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import logcat.logcat

class ExcludeGesturesConstraintLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var exclusionRects: List<Rect>? = null
    private var insets: Insets = Insets.of(0, 0, 0, 0)

    private val fillPaint = Paint().apply {
        style = Paint.Style.FILL
        color = context.getColor(R.color.primary).and(0x80FFFFFF.toInt())
    }

    init {
        setWillNotDraw(false)
    }

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        val exclusionRects = if (insets.left == 0 || insets.right == 0) {
            listOf()
        } else if (changed || this.exclusionRects == null) {
            listOf(
                Rect(left, top, left + insets.left, bottom),
                Rect(right - insets.right, top, right, bottom)
            )
        } else {
            this.exclusionRects!!
        }
        logcat { "setSystemGestureExclusionRects $exclusionRects" }
        ViewCompat.setSystemGestureExclusionRects(this, exclusionRects)
    }

    fun setWindowInsets(insets: Insets) {
        logcat { "setWindowInsets $insets" }
        this.insets = insets
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(0f, 0f, width.toFloat(), insets.top.toFloat(), fillPaint)
        canvas.drawRect(
            0f,
            height - insets.top.toFloat(),
            width.toFloat(),
            height.toFloat(),
            fillPaint
        )
        canvas.drawRect(0f, 0f, insets.left.toFloat(), height.toFloat(), fillPaint)
        canvas.drawRect(
            width - insets.right.toFloat(),
            0f,
            width.toFloat(),
            height.toFloat(),
            fillPaint
        )
    }
}