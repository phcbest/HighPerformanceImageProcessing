package org.phcbest.imagecrop

import android.graphics.RectF

object BoxHelper {

    /**
     * The essence of control is to keep the diagonal points constant
     *  control top
     *  0------1
     *  |      |
     *  |      |
     *  |      |
     *  3------2
     */
    fun limitAspectTop(rectF: RectF, enableAspectRatioScale: Boolean, aspectRatio: Pair<Int, Int>) {
        if (enableAspectRatioScale) {
            val boxAspect = rectF.width() / rectF.height()
            val settingAspect = aspectRatio.first / aspectRatio.second.toFloat()
            if (boxAspect != settingAspect) {
                rectF.set(rectF.left, rectF.bottom - (rectF.width() / settingAspect), rectF.right, rectF.bottom)
            }
        }
    }

    /**
     *  control bottom
     *  0------1
     *  |      |
     *  |      |
     *  |      |
     *  3------2
     */
    fun limitAspectBottom(rectF: RectF, enableAspectRatioScale: Boolean, aspectRatio: Pair<Int, Int>) {
        if (enableAspectRatioScale) {
            val boxAspect = rectF.width() / rectF.height()
            val settingAspect = aspectRatio.first / aspectRatio.second.toFloat()
            if (boxAspect != settingAspect) {
                rectF.set(rectF.left, rectF.top, rectF.right, rectF.top + (rectF.width() / settingAspect))
            }
        }
    }


    fun bottomLimit(boxRectF: RectF, boxMinimumLimit: Pair<Float, Float>) {
        if (boxRectF.height() < boxMinimumLimit.second) {
            boxRectF.bottom = boxRectF.top + boxMinimumLimit.second
        }
    }

    fun rightLimit(boxRectF: RectF, boxMinimumLimit: Pair<Float, Float>) {
        if (boxRectF.width() < boxMinimumLimit.first) {
            boxRectF.right = boxRectF.left + boxMinimumLimit.first
        }
    }

    fun topLimit(boxRectF: RectF, boxMinimumLimit: Pair<Float, Float>) {
        if (boxRectF.height() < boxMinimumLimit.second) {
            boxRectF.top = boxRectF.bottom - boxMinimumLimit.second
        }
    }

    fun leftLimit(boxRectF: RectF, boxMinimumLimit: Pair<Float, Float>) {
        if (boxRectF.width() < boxMinimumLimit.first) {
            boxRectF.left = boxRectF.right - boxMinimumLimit.first
        }
    }
}