package org.phcbest.highperformanceimagenative.easylut.filter;

import android.graphics.Bitmap;
import android.util.Log;

import org.phcbest.highperformanceimagenative.easylut.lutimage.LUTImage;


public class CreatingNewBitmap implements BitmapStrategy {
    @Override
    public Bitmap applyLut(Bitmap src, LUTImage lutImage) {
        long startTime = System.currentTimeMillis();
        int mWidth = src.getWidth();//原图宽度
        int mHeight = src.getHeight();//原图高度
        int[] pix = new int[mWidth * mHeight];//能够容纳原图的数组
        //原图复制到pix数组中
        src.getPixels(pix, 0, mWidth, 0, 0, mWidth, mHeight);//bitmap 原生方法,将图片所有的像素信息读取到数组中

        //从上到下,从左到右遍历原图
        Log.i(TAG, "applyLut: 图片宽"+mWidth+"高"+mHeight);
        for (int y = 0; y < mHeight; y++) {
            for (int x = 0; x < mWidth; x++) {
                int index = y * mWidth + x;
                int pixel = pix[index];
                pix[index] = lutImage.getColorPixelOnLut(pixel);
                if (x == 111 && y == 111) {
                    lutImage.getColorPixelOnLutLog(pixel);
                    Log.i(TAG, "applyLut: index" + index);
                    Log.i(TAG, "applyLut: pixel" + pixel + "=" + Integer.toHexString(pixel));
                }
                if (x > 100 && x < 500 && y > 100 && y < 500) {
                    pix[index] = 0xff000000;
                }
            }
        }
        Bitmap bm = Bitmap.createBitmap(mWidth, mHeight, src.getConfig());
        bm.setPixels(pix, 0, mWidth, 0, 0, mWidth, mHeight);
//        Log.i(TAG, "applyLut: TimeConsuming" + (System.currentTimeMillis() - startTime));
        return bm;
    }
}
