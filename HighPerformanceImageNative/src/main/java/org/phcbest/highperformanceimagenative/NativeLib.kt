package org.phcbest.highperformanceimagenative

import android.graphics.Bitmap
import android.graphics.Rect

internal class NativeLib {

    external fun getImageMinPixelSize(bitmap: Bitmap): Rect
    external fun getImageMinPixelSizePrecisionControl(bitmap: Bitmap, precision: Int): Rect

//    external fun getLutFilterImage(bitmap: Bitmap, lutBitmap: Bitmap): Bitmap

    companion object {
        init {
            System.loadLibrary("highperformanceimagenative")
        }
    }
}