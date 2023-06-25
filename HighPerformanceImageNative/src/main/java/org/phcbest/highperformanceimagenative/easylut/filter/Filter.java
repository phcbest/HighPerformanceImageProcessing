package org.phcbest.highperformanceimagenative.easylut.filter;

import android.graphics.Bitmap;
import android.widget.ImageView;

public interface Filter {

    Bitmap apply(Bitmap src);

    void apply(ImageView imageView);

}
