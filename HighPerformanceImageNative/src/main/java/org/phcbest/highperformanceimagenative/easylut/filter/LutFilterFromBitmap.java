package org.phcbest.highperformanceimagenative.easylut.filter;


import android.graphics.Bitmap;

import org.phcbest.highperformanceimagenative.easylut.lutimage.CoordinateToColor;
import org.phcbest.highperformanceimagenative.easylut.lutimage.LutAlignment;


public class LutFilterFromBitmap extends LUTFilter {

    private final Bitmap bitmap;

    private LutFilterFromBitmap(Bitmap bitmap, BitmapStrategy strategy,
                                CoordinateToColor.Type coordinateToColorType,
                                LutAlignment.Mode lutAlignmentMode) {
        super(strategy, coordinateToColorType, lutAlignmentMode);
        this.bitmap = bitmap;
    }

    protected Bitmap getLUTBitmap() {
        return bitmap;
    }

    public static class Builder extends LUTFilter.Builder<Builder> {

        private Bitmap bitmap;

        public Builder withBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
            return this;
        }

        public Filter createFilter() {
            return new LutFilterFromBitmap(bitmap, strategy,
                    coordinateToColorType, lutAlignmentMode);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
