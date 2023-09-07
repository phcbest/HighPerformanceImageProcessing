package org.phcbest.imagecrop

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout

/**
 * pictureEditor
 */
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


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) {
            return true
        }
        pCropIndicator.scaleBox(event)
        return true
    }

    private fun reSetImage() {
        pCropImage.setImage(inputUri, 2000 to 2000)
    }

}