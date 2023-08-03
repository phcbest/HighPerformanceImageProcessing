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
        val assetManager = assets
        val testimage = assetManager.open("test_image.jpg")
        val testlut = assetManager.open("test_lut.png")
        val filtersquare8nintendo = assetManager.open("filter_square_8_nintendo.png")

        val testimageBmp = BitmapFactory.decodeStream(testimage)
        val testlutBmp = BitmapFactory.decodeStream(testlut)
        val filtersquare8nintendoBmp = BitmapFactory.decodeStream(filtersquare8nintendo)
        testimage.close()
        testlut.close()
        filtersquare8nintendo.close()

        val filter = EasyLUT.fromBitmap()
            .withBitmap(testlutBmp).createFilter()
        val applyFilterImage =
            filter.apply(testimageBmp)

        val imageByLutFilter =
            ImageHelper.getImageByLutFilter(testimageBmp, filtersquare8nintendoBmp)
        findViewById<ImageView>(R.id.iv_origin).setImageBitmap(testimageBmp)
        findViewById<ImageView>(R.id.iv_lut).setImageBitmap(applyFilterImage)
        findViewById<ImageView>(R.id.iv_cpplut).setImageBitmap(imageByLutFilter)
    }
}