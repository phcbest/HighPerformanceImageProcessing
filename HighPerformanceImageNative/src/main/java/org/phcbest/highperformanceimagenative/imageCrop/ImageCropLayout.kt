package org.phcbest.highperformanceimagenative.imageCrop

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

class ImageCropLayout : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        val imageCropIndexView = ImageCropIndexView(context)
        val imageCropMaterialView = ImageCropMaterialView(context)
        addView(imageCropMaterialView)
        addView(imageCropIndexView)
    }


}