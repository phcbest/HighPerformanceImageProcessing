package org.phcbest.highperformanceimageprocessing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import org.phcbest.highperformanceimageprocessing.lut.LutFilterActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btn_lut_filter).setOnClickListener(this::onClick)
    }

    private fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_lut_filter -> {
                startActivity(Intent(this, LutFilterActivity::class.java))
            }

            else -> {}
        }
    }
}