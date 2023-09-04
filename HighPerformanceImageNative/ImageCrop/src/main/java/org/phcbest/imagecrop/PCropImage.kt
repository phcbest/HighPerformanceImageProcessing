package org.phcbest.imagecrop

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.View

internal class PCropImage : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
}