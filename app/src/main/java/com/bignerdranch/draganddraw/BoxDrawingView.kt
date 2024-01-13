package com.bignerdranch.draganddraw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class BoxDrawingView(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {
    private var currentBox: Box? = null
    private val boxes = mutableListOf<Box>()
    private val boxPaint = Paint().apply {
        color = 0x22ff0000.toInt()
    }
    private val backGroundPaint = Paint().apply {
        color = 0xfff8efe0.toInt()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPaint(backGroundPaint)

        boxes.forEach {box ->
            canvas.drawRect(box.left, box.top, box.right, box.bottom, boxPaint)
            canvas.drawCircle((box.left+box.right)/2, (box.top + box.bottom)/2, (box.right - box.left)/2, Paint().apply { color = 0x22ff0000.toInt() })
        }
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val current = PointF(event.x, event.y)
        var action = ""
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                action = "ACTION_DOWN"
                currentBox = Box(current).also {
                    boxes.add(it)
                }
            }
            MotionEvent.ACTION_MOVE -> {
                action = "ACTION_MOVE"
            }
            MotionEvent.ACTION_UP -> {
                action = "Out "
                updateCurrentBox(current)
                currentBox = null
            }
            MotionEvent.ACTION_CANCEL -> {
                action = "EEEH"
                currentBox = null
            }
        }
        return true
    }
    private fun updateCurrentBox(current: PointF) {
        currentBox?.run {
            end = current
            invalidate()
        }
    }

}

