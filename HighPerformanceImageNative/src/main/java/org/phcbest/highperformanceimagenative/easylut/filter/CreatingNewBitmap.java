package org.phcbest.highperformanceimagenative.easylut.filter;

import android.graphics.Bitmap;
import android.util.Log;

import org.phcbest.highperformanceimagenative.easylut.lutimage.LUTImage;


public class CreatingNewBitmap implements BitmapStrategy {
    @Override
    public Bitmap applyLut(Bitmap src, LUTImage lutImage) {
        long startTime = System.currentTimeMillis();
        int mWidth = src.getWidth();
        int mHeight = src.getHeight();
        int[] pix = new int[mWidth * mHeight];
        src.getPixels(pix, 0, mWidth, 0, 0, mWidth, mHeight);

        for (int y = 0; y < mHeight; y++) {
            for (int x = 0; x < mWidth; x++) {
                int index = y * mWidth + x;
                int pixel = pix[index];

                pix[index] = lutImage.getColorPixelOnLut(pixel);
            }
        }
        Bitmap bm = Bitmap.createBitmap(mWidth, mHeight, src.getConfig());
        bm.setPixels(pix, 0, mWidth, 0, 0, mWidth, mHeight);
        Log.i(TAG, "applyLut: TimeConsuming" + (System.currentTimeMillis() - startTime));
        return bm;
    }
}
