package org.phcbest.highperformanceimageprocessing.encryptBitmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import org.phcbest.highperformanceimageprocessing.R

class EncrypyBitmapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encrypy_bitmap)
        var btnEncryption = findViewById<Button>(R.id.btn_encryption)
        var btnDecrypt = findViewById<Button>(R.id.btn_decrypt)
        var ivBmp = findViewById<ImageView>(R.id.iv_bmp)
        
    }
}