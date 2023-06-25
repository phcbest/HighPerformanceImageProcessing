package org.phcbest.highperformanceimagenative.easylut.filter;


import android.graphics.Bitmap;

import org.phcbest.highperformanceimagenative.easylut.lutimage.LUTImage;


public interface BitmapStrategy {

    String TAG = BitmapStrategy.class.getSimpleName();

    Bitmap applyLut(Bitmap src, LUTImage lutImage);

    enum Type{
        APPLY_ON_ORIGINAL_BITMAP, CREATING_NEW_BITMAP
    }
}
