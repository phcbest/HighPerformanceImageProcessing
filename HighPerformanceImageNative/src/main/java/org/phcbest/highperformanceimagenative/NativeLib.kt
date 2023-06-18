package org.phcbest.highperformanceimagenative

import android.graphics.Bitmap
import android.graphics.Rect

internal class NativeLib {

    external fun getImageMinPixelSize(bitmap: Bitmap): Rect

    companion object {
        init {
            System.loadLibrary("highperformanceimagenative")
        }
    }
}