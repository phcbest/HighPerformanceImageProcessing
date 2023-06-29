package org.phcbest.highperformanceimagenative

import android.graphics.Bitmap
import android.graphics.Rect

object ImageHelper {
    private var nativeLib = NativeLib()

    fun getHasPixelRectSize(bitmap: Bitmap): Rect {
        return nativeLib.getImageMinPixelSize(bitmap)
    }

    fun getHasPixelRectSize(bitmap: Bitmap, precision: Int): Rect {
        return nativeLib.getImageMinPixelSizePrecisionControl(bitmap, precision)
    }

    fun getImageByLutFilter(bitmap: Bitmap, lutBitmap: Bitmap): Bitmap {
        return nativeLib.getLutFilterImage(bitmap, lutBitmap)
    }

}