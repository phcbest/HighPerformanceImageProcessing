package org.phcbest.highperformanceimagenative.easylut.filter;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.phcbest.highperformanceimagenative.easylut.lutimage.CoordinateToColor;
import org.phcbest.highperformanceimagenative.easylut.lutimage.LutAlignment;


public class LutFilterFromResource extends LUTFilter {

    private final Resources resources;
    private final int lutBitmapId;

    private LutFilterFromResource(Resources resources, int lutBitmapId, BitmapStrategy strategy,
                                  CoordinateToColor.Type coordinateToColorType,
                                  LutAlignment.Mode lutAlignmentMode) {
        super(strategy, coordinateToColorType, lutAlignmentMode);
        this.resources = resources;
        this.lutBitmapId = lutBitmapId;
    }

    protected Bitmap getLUTBitmap() {
        return BitmapFactory.decodeResource(resources, lutBitmapId);
    }

    public static class Builder extends LUTFilter.Builder<Builder> {

        private Resources resources;
        private int lutBitmapId;

        public Builder withResources(Resources resources) {
            this.resources = resources;
            return this;
        }

        public Builder withLutBitmapId(int lutBitmapId) {
            this.lutBitmapId = lutBitmapId;
            return this;
        }

        public Filter createFilter() {
            return new LutFilterFromResource(resources, lutBitmapId, strategy,
                    coordinateToColorType, lutAlignmentMode);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
