package org.phcbest.imagecrop

import android.content.res.Resources

val Number.dp: Int get() = (toInt() * Resources.getSystem().displayMetrics.density).toInt()
val Number.dpF: Float get() = (toInt() * Resources.getSystem().displayMetrics.density)
