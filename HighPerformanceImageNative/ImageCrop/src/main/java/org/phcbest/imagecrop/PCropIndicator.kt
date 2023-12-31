package org.phcbest.imagecrop

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

private const val TAG = "PCropIndicator"

internal class PCropIndicator : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
    }

    /**
     * restricted scaling can only be proportional
     */
    var enableAspectRatioScale = true

    /**
     * aspectRatio
     * first is width
     * second is height
     */
    var aspectRatio: Pair<Int, Int> = Pair(1, 1)
        set(value) {
            field = value
            doSelectBoxChange()
        }


    /**
     * minimumLimit
     * first is width
     * second is height
     */
    private var boxMinimumLimit: Pair<Float, Float> = 100.dpF to 100.dpF

    private fun doSelectBoxChange() {

    }

    private var boxRectF = RectF(0F, 0F, 100.dpF, 100.dpF)
    private var selectPosition = -1

    /**
     * schematic diagram of displacement point
     *
     *  0------1
     *  |      |
     *  |      |
     *  |      |
     *  3------2
     */
    fun scaleBox(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                //判断是否点击四个点
                val pointF = PointF(event.x, event.y)
                selectPosition = clickCorner(pointF)
                if (selectPosition != -1) {
                    lastPointF = pointF
                }
            }

            MotionEvent.ACTION_MOVE -> {
                boxRectSelectZoomChange(selectPosition, event)
            }

            MotionEvent.ACTION_UP -> {
                selectPosition = -1
            }

            else -> {}
        }
    }

    private var lastPointF = PointF()

    /**
     * 选择区域变化
     */
    private fun boxRectSelectZoomChange(selectPosition: Int, event: MotionEvent) {
        if (selectPosition == -1) {
            return
        }
        val xTrans = event.x - lastPointF.x
        val yTrans = event.y - lastPointF.y
        when (selectPosition) {
            0 -> {
                boxRectF.top += yTrans
                boxRectF.left += xTrans
                BoxHelper.leftLimit(boxRectF, boxMinimumLimit)
                BoxHelper.topLimit(boxRectF, boxMinimumLimit)
                BoxHelper.limitAspectTop(boxRectF, enableAspectRatioScale, aspectRatio)
            }

            1 -> {
                boxRectF.top += yTrans
                boxRectF.right += xTrans
                BoxHelper.rightLimit(boxRectF, boxMinimumLimit)
                BoxHelper.topLimit(boxRectF, boxMinimumLimit)
                BoxHelper.limitAspectTop(boxRectF, enableAspectRatioScale, aspectRatio)
            }

            2 -> {
                boxRectF.bottom += yTrans
                boxRectF.right += xTrans
                BoxHelper.rightLimit(boxRectF, boxMinimumLimit)
                BoxHelper.bottomLimit(boxRectF, boxMinimumLimit)
                BoxHelper.limitAspectBottom(boxRectF, enableAspectRatioScale, aspectRatio)
            }

            3 -> {
                boxRectF.bottom += yTrans
                boxRectF.left += xTrans
                BoxHelper.leftLimit(boxRectF, boxMinimumLimit)
                BoxHelper.bottomLimit(boxRectF, boxMinimumLimit)
                BoxHelper.limitAspectBottom(boxRectF, enableAspectRatioScale, aspectRatio)
            }
        }
        BoxHelper.limitInRect(boxRectF, Rect(0, 0, width, height),selectPosition)
        invalidate()
        lastPointF.set(event.x, event.y)
    }


    private var clickTolerance = 15.dp
    private fun clickCorner(pointf: PointF): Int {
        if (pointf.y in boxRectF.top - clickTolerance..boxRectF.top + clickTolerance &&
            pointf.x in boxRectF.left - clickTolerance..boxRectF.left + clickTolerance
        ) {
            return 0
        }
        if (pointf.y in boxRectF.top - clickTolerance..boxRectF.top + clickTolerance &&
            pointf.x in boxRectF.right - clickTolerance..boxRectF.right + clickTolerance
        ) {
            return 1
        }
        if (pointf.y in boxRectF.bottom - clickTolerance..boxRectF.bottom + clickTolerance &&
            pointf.x in boxRectF.right - clickTolerance..boxRectF.right + clickTolerance
        ) {
            return 2
        }
        if (pointf.y in boxRectF.bottom - clickTolerance..boxRectF.bottom + clickTolerance &&
            pointf.x in boxRectF.left - clickTolerance..boxRectF.left + clickTolerance
        ) {
            return 3
        }
        return -1
    }


    private var selectBoxPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        this.color = Color.BLACK
        this.style = Paint.Style.STROKE
        this.strokeWidth = 2.dpF
    }
    private var selectBoxHollowOutPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        this.color = Color.WHITE
        this.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    private var maskColor = Color.parseColor("#33000000")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val saveLayer = canvas?.saveLayer(0F, 0F, width.toFloat(), height.toFloat(), null)
        canvas?.drawColor(maskColor)
        canvas?.drawRect(boxRectF, selectBoxHollowOutPaint)
        saveLayer?.let { canvas.restoreToCount(it) }
        canvas?.drawRect(boxRectF, selectBoxPaint)
    }

}