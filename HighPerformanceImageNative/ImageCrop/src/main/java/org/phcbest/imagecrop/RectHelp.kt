package org.phcbest.imagecrop

import android.graphics.Rect
import android.graphics.RectF


fun Rect.scaleThanWH(width: Int, height: Int): Float {
    val widthScale = this.width() / width.toFloat()
    val heightScale = this.height() / height.toFloat()
    return minOf(widthScale, heightScale)
}
