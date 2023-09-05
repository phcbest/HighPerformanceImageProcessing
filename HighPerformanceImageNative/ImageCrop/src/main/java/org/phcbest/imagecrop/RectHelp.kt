package org.phcbest.imagecrop

import android.graphics.Rect


fun Rect.scaleThanWH(width: Int, height: Int): Float {
    val widthScale = this.width() / width.toFloat()
    val heightScale = this.height() / height.toFloat()
    return minOf(widthScale, heightScale)

}
