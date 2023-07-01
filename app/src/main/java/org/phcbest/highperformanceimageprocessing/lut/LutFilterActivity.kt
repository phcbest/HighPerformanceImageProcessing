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
        val testImage = BitmapFactory.decodeResource(this.resources, R.raw.test_image)
//        val filter = EasyLUT.fromBitmap()
//            .withBitmap(
//                BitmapFactory.decodeResource(
//                    this.resources,
//                    R.mipmap.test_lut
//                )
//            ).createFilter()
//        val applyFilterImage =
//            filter.apply(BitmapFactory.decodeResource(this.resources, R.raw.test_image))
        val applyFilterImage = ImageHelper.getImageByLutFilter(
            testImage, BitmapFactory.decodeResource(
                this.resources,
                R.mipmap.filter_square_8_nintendo
            )
        )
        findViewById<ImageView>(R.id.iv_origin).setImageBitmap(testImage)
        findViewById<ImageView>(R.id.iv_lut).setImageBitmap(applyFilterImage)
    }
}