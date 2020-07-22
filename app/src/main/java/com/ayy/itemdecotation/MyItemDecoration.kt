package com.ayy.itemdecotation

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.min

abstract class MyItemDecoration<T>(val data: List<T>) : RecyclerView.ItemDecoration() {
    private var mPaint: Paint
    private var mTextPaint: TextPaint
    private val dividerHeight = 10f
    private val headerHeight = 100f

    abstract fun isHeader(position: Int): Boolean
    abstract fun getText(position: Int): String

    init {
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.isDither = true
        mPaint.color = Color.GRAY
        mPaint.style = Paint.Style.FILL

        mTextPaint = TextPaint()
        mTextPaint.isAntiAlias = true
        mTextPaint.isDither = true
        mTextPaint.color = Color.WHITE
        mTextPaint.textSize = 40f
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildLayoutPosition(view)
        if (isHeader(position)) {
            outRect.set(0, headerHeight.toInt(), 0, 0)
        } else {
            outRect.set(0, dividerHeight.toInt(), 0, 0)
        }
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(canvas, parent, state)
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        for (i in 0 until parent.childCount) {
            val view = parent.getChildAt(i)
            val position = parent.getChildLayoutPosition(view)
            val bottom = view.top
            var top = view.top - dividerHeight
            if (isHeader(position)) {
                top = view.top - headerHeight
                if (isLegalArea(top.toInt(), bottom, parent)) {
                    mPaint.color = Color.RED
                    canvas.drawRect(left.toFloat(), top, right.toFloat(), bottom.toFloat(), mPaint)
                    val groupName = getText(position)
                    val baseline = top +
                            headerHeight / 2 - (mPaint.fontMetrics.ascent + mPaint.fontMetrics.descent) / 2
                    canvas.drawText(groupName, left.toFloat(), baseline, mTextPaint)
                }
            } else {
                if (isLegalArea(top.toInt(), bottom, parent)) {
                    mPaint.color = Color.GRAY
                    canvas.drawRect(left.toFloat(), top, right.toFloat(), bottom.toFloat(), mPaint)
                }
            }
        }
    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(canvas, parent, state)
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        var top = parent.paddingTop
        var bottom = top + headerHeight
        if (parent.layoutManager is LinearLayoutManager) {
            val firstVisiblePosition =
                (parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            //达到条件：下一组的top小于2*headerHeight
            val nextHeaderPosition = findNextGroupHeader(firstVisiblePosition)
            if (nextHeaderPosition >= 0) {
                val nextView =
                    parent.findViewHolderForLayoutPosition(nextHeaderPosition)?.itemView
                if (nextView != null) {
                    if (nextView.top - parent.paddingTop < 2 * headerHeight) {
                        bottom = min(bottom, nextView.top-headerHeight)
                    }
                }
            }
            mPaint.color = Color.RED
            canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom, mPaint)
            val groupName = getText(firstVisiblePosition)
            val baseline =
                bottom - headerHeight + (headerHeight / 2 - (mPaint.fontMetrics.ascent + mPaint.fontMetrics.descent) / 2)
            canvas.save()
            canvas.clipRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom)
            canvas.drawText(groupName, left.toFloat(), baseline, mTextPaint)
            canvas.restore()
        }
    }

    private fun findNextGroupHeader(currentPosition: Int): Int {
        if (currentPosition >= data.size - 1) {
            return -1
        }
        for (i in currentPosition + 1 until data.size) {
            if (isHeader(i)) {
                return i
            }
        }
        return -1
    }

    private fun isLegalArea(top: Int, bottom: Int, parent: View): Boolean {
        if (top <= parent.paddingTop || bottom >= parent.height - parent.paddingBottom) {
            return false
        }
        return true
    }
}