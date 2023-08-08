package org.phcbest.highperformanceimagenative.encryptPictures

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream


/**
 * 使用插入字节的方法来进行加密,防止用户能够直接看到图片
 */
object EncryptBitmap {
    private const val ENCRYPT_BYTE = "ENCRYPT_BYTE"
    suspend fun encryptBitmap(bitmap: Bitmap, path: String) {
        try {
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val bytes = baos.toByteArray()
            //混入的字节流
            val bytesAdd: ByteArray = ENCRYPT_BYTE.toByteArray()
            withContext(Dispatchers.IO) {
                val fops = FileOutputStream(path)
                fops.write(bytesAdd)
                fops.write(bytes)
                fops.flush()
                fops.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun decryptBitmap(encryptPath: String): String {
        TODO("decryptBitmap待实现")
    }
}