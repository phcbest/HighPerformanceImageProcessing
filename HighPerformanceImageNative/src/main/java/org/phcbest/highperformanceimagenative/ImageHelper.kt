package org.phcbest.highperformanceimagenative

import android.graphics.Bitmap
import android.graphics.Rect

object ImageHelper {
    private var nativeLib = NativeLib()

    fun getHasPixelRectSize(bitmap: Bitmap): Rect {
        return nativeLib.getImageMinPixelSize(bitmap)
    }

}