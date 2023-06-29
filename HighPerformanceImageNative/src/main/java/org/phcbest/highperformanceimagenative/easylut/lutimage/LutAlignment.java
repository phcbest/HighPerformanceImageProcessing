package org.phcbest.highperformanceimagenative.easylut.lutimage;


import android.util.Log;

public interface LutAlignment {

    String TAG = LutAlignment.class.getSimpleName();

    int getX(int rowDepth, int sideSize, int x, int y, int z);

    int getY(int rowDepth, int sideSize, int x, int y, int z);

    class Square implements LutAlignment {
        public Square() {
            Log.i(TAG, "Square");
        }

        @Override
        public int getX(int rowDepth, int sideSize, int x, int y, int z) {
            return (rowDepth == 1 ? z : z % rowDepth) * sideSize + x;
        }

        @Override
        public int getY(int rowDepth, int sideSize, int x, int y, int z) {
            return (rowDepth == 1 ? 0 : z / rowDepth) * sideSize + y;
        }

    }

    class Hald implements LutAlignment {
        public Hald() {
            Log.i(TAG, "Hald");
        }

        @Override
        public int getX(int rowDepth, int sideSize, int x, int y, int z) {
            int red = x;
            int green = y % rowDepth * sideSize;
            return red + green;
        }

        @Override
        public int getY(int rowDepth, int sideSize, int x, int y, int z) {
            int green = y / rowDepth;
            int blue = z * rowDepth;
            return green + blue;
        }

    }

    enum Mode {
        SQUARE {
            @Override
            LutAlignment getLutAlignment() {
                return new Square();
            }
        },
        HALD {
            @Override
            LutAlignment getLutAlignment() {
                return new Hald();
            }
        };

        abstract LutAlignment getLutAlignment();
    }
}
