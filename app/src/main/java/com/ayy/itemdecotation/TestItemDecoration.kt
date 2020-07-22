package com.ayy.itemdecotation

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class TestItemDecoration : RecyclerView.ItemDecoration() {
    private var mPaint: Paint

    init {
        mPaint = Paint()
        mPaint.color = Color.RED
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(10, 10, 50, 10)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
//        for (i in 0 until parent.childCount) {
//            val view = parent.getChildAt(i)
//            val left = parent.paddingLeft
//            val right = parent.width - parent.paddingRight
//            c.drawRect(
//                right - 100f,
//                view.bottom.toFloat(),
//                right.toFloat(),
//                view.bottom + 80f,
//                mPaint
//            )
//        }

    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        for (i in 0 until parent.childCount) {
            val view = parent.getChildAt(i)
            val left = parent.paddingLeft
            val right = parent.width - parent.paddingRight
            c.drawRect(
                right - 100f,
                view.bottom.toFloat(),
                right.toFloat(),
                view.bottom + 80f,
                mPaint
            )
        }
    }
}