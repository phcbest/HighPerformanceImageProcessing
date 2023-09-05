package org.phcbest.highperformanceimageprocessing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.blankj.utilcode.util.PathUtils
import org.phcbest.imagecrop.PCrop
import java.io.File

class ImageCropActivity : AppCompatActivity() {
    private val mPcrop: PCrop by lazy { findViewById<PCrop>(R.id.pcrop) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_crop)
        val demo = File(PathUtils.getExternalAppCachePath(), "demo.png").toUri()
        val outPath = File(PathUtils.getExternalAppCachePath(), "output.png").toUri()
        mPcrop.setInputOutput(demo, outPath)
    }
}