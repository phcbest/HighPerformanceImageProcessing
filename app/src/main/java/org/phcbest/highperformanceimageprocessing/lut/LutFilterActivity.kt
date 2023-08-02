package org.phcbest.highperformanceimageprocessing.lut

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import org.phcbest.highperformanceimagenative.ImageHelper
import org.phcbest.highperformanceimagenative.easylut.EasyLUT
import org.phcbest.highperformanceimageprocessing.R

class LutFilterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lut_filter)
        val filter = EasyLUT.fromBitmap()
            .withBitmap(
                BitmapFactory.decodeResource(
                    this.resources,
                    R.mipmap.test_lut
                )
            ).createFilter()
        val bbb = BitmapFactory.decodeResource(this.resources, R.mipmap.test_image)
        val applyFilterImage =
            filter.apply(bbb)

        val testImage = BitmapFactory.decodeResource(this.resources, R.mipmap.test_image)
        val imageByLutFilter = ImageHelper.getImageByLutFilter(
            testImage, BitmapFactory.decodeResource(
                this.resources,
                R.mipmap.filter_square_8_nintendo
            )
        )
        findViewById<ImageView>(R.id.iv_origin).setImageBitmap(testImage)
        findViewById<ImageView>(R.id.iv_lut).setImageBitmap(applyFilterImage)
        findViewById<ImageView>(R.id.iv_cpplut).setImageBitmap(imageByLutFilter)
    }
}