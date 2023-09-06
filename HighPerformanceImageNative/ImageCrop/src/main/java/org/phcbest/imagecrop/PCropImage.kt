package org.phcbest.imagecrop

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Rect
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class PCropImage : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    private var dstImage: Bitmap? = null
    private var bitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var bitmapMatrix = Matrix()
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.concat(bitmapMatrix)
        dstImage?.let {
            canvas?.drawBitmap(it, 0F, 0F, bitmapPaint)
        }
    }

    private var originToDstScale = 1F
    fun setImage(inputUri: Uri?, limitPixelSize: Pair<Int, Int>? = null) {
        CoroutineScope(Dispatchers.Default).launch {
            inputUri?.encodedPath?.let {
                val originBitmap =
                    BitmapFactory.decodeStream(context.contentResolver.openInputStream(inputUri))
                dstImage = if (limitPixelSize != null) {
                    originToDstScale =
                        Rect(0, 0, limitPixelSize.first, limitPixelSize.second).scaleThanWH(originBitmap.width, originBitmap.height)
                    Bitmap.createScaledBitmap(
                        originBitmap,
                        (originBitmap.width * originToDstScale).toInt(),
                        (originBitmap.height * originToDstScale).toInt(),
                        true
                    )
                } else {
                    originToDstScale = 1F
                    originBitmap
                }
                //计算bitmap放入view的矩阵大小,居中
                val viewRect = Rect()
                this@PCropImage.getDrawingRect(viewRect)
                val scaleThanWH = viewRect.scaleThanWH(dstImage!!.width, dstImage!!.height)
                bitmapMatrix.postScale(scaleThanWH, scaleThanWH)
                bitmapMatrix.postTranslate(
                    (viewRect.width() - dstImage!!.width * scaleThanWH) / 2F,
                    (viewRect.height() - dstImage!!.height * scaleThanWH) / 2F
                )
                withContext(Dispatchers.Main) {
                    invalidate()
                }
            }
        }
    }
}
