package org.phcbest.highperformanceimageprocessing

import android.graphics.Paint
import android.os.Bundle
import android.util.Pair
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.PathUtils
import com.blankj.utilcode.util.SizeUtils
import org.phcbest.imagecrop.view.OverlayView
import org.phcbest.imagecrop.view.OverlayView.FreestyleMode
import org.phcbest.imagecrop.view.UCropView
import java.io.File

class ImageCropActivity : AppCompatActivity() {

    private val mUcrop: UCropView by lazy { findViewById<UCropView>(R.id.ucrop) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_crop)
        val demo = File(PathUtils.getExternalAppCachePath(), "demo.png").toUri()
        val outPath = File(PathUtils.getExternalAppCachePath(), "output.png").toUri()
        mUcrop.cropImageView.setImageUri(demo, outPath)
        mUcrop.overlayView.freestyleCropMode =
            OverlayView.FREESTYLE_CROP_MODE_ENABLE_WITH_PASS_THROUGH
        mUcrop.overlayView.setTargetAspectRatio(16F / 9F)
        mUcrop.overlayView.setCropRectMinWH(
            (Pair(
                SizeUtils.dp2px(50F),
                SizeUtils.dp2px(50 * (9F / 16F))
            ))
        )
        mUcrop.overlayView.isFreeStyleLockAspectRatio = true
    }
}