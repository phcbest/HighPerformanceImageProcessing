package org.phcbest.imagecrop

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.FrameLayout
import java.util.Objects

/**
 * pictureEditor
 */
private const val TAG = "PCrop"

class PCrop : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var pCropImage = PCropImage(context)
    private var pCropIndicator = PCropIndicator(context)

    init {
        addView(pCropImage)
        addView(pCropIndicator)
    }

    private var inputUri: Uri? = null
    private var outputUri: Uri? = null

    /**
     * 用来设置输入和输出图片的地址
     */
    fun setInputOutput(input: Uri, output: Uri) {
        inputUri = input
        outputUri = output
        reSetImage()
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return true
    }


    private var scaleGestureDetector = ScaleGestureDetector(context, object : ScaleGestureDetector.OnScaleGestureListener {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            Log.i(TAG, "onScale: ")
            return true
        }

        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            Log.i(TAG, "onScaleBegin: ")
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector) {
        }

    })

    private var isOnceTwoFingers = false
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) {
            return true
        }
        if (event.pointerCount >= 2) {//If two-finger gestures are used, in order to avoid false touches, only zooming is allowed
            scaleGestureDetector.onTouchEvent(event)
            isOnceTwoFingers = true
        } else {
            if (!isOnceTwoFingers) {
                pCropIndicator.scaleBox(event)
            }
        }
        if (event.action == MotionEvent.ACTION_UP) {
            isOnceTwoFingers = false
        }
        return true
    }

    private fun reSetImage() {
        pCropImage.setImage(inputUri, 2000 to 2000)
    }

}