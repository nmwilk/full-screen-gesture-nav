package com.nmwilkinson.fullscreengesturenav

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View

class DrawView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    private var lastAction: Int? = null
    private val points = mutableListOf<PointF>()

    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        strokeWidth = 5f
        color = Color.YELLOW
    }

    private val textPaint = TextPaint().apply {
        strokeWidth = 5f
        textSize = 40f
        textAlign = Paint.Align.CENTER
        color = Color.WHITE
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        lastAction = event?.action
        when (event?.action) {
            ACTION_DOWN -> {
                points.clear()
                points.add(PointF(event.x, event.y))
                invalidate()
            }
            ACTION_MOVE -> {
                points.add(PointF(event.x, event.y))
                invalidate()
            }
            ACTION_CANCEL -> {
                points.clear()
                invalidate()
            }
            ACTION_UP -> {
                invalidate()
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        points.indices.forEach { index ->
            if (index > 0) {
                val start = points[index - 1]
                val end = points[index]
                canvas.drawLine(start.x, start.y, end.x, end.y, strokePaint)
            }
        }

        lastAction?.let { canvas.drawText("Last action: ${actionName(it)}", width / 2f, height / 2f, textPaint) }
    }

    private fun actionName(motionEventAction: Int) = when(motionEventAction) {
        ACTION_CANCEL -> "Cancel"
        ACTION_UP -> "Up"
        ACTION_DOWN -> "Down"
        ACTION_MOVE -> "Move"
        else -> "Unknown"
    }
}