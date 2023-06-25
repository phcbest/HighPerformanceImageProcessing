package org.phcbest.highperformanceimagenative.easylut;


import org.phcbest.highperformanceimagenative.easylut.filter.Filter;
import org.phcbest.highperformanceimagenative.easylut.filter.FilterNon;
import org.phcbest.highperformanceimagenative.easylut.filter.LutFilterFromBitmap;
import org.phcbest.highperformanceimagenative.easylut.filter.LutFilterFromResource;

public class EasyLUT {

    public static LutFilterFromResource.Builder fromResourceId() {
        return new LutFilterFromResource.Builder();
    }

    public static LutFilterFromBitmap.Builder fromBitmap() {
        return new LutFilterFromBitmap.Builder();
    }

    public static Filter createNonFilter() {
        return new FilterNon();
    }

}
