package org.phcbest.highperformanceimageprocessing.lut

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import org.phcbest.highperformanceimagenative.ImageHelper
import org.phcbest.highperformanceimageprocessing.R

class LutFilterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lut_filter)
        val lut = BitmapFactory.decodeResource(this.resources, R.raw.test_lut)
        val testImage = BitmapFactory.decodeResource(this.resources, R.raw.test_image)
        val lutFilterImage = ImageHelper.getLutFilterImage(testImage, lut)
        findViewById<ImageView>(R.id.iv_origin).setImageBitmap(testImage)
        findViewById<ImageView>(R.id.iv_lut).setImageBitmap(lutFilterImage)
    }
}