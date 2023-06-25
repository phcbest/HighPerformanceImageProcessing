#include <jni.h>
#include "imageMinPixelSize.h"
#include "lutFilterImage.h"


extern "C"
JNIEXPORT jobject JNICALL
Java_org_phcbest_highperformanceimagenative_NativeLib_getImageMinPixelSize(
        JNIEnv *env,
        jobject thiz,
        jobject bitmap) {
    return getImageMinPixelSize(env, thiz, bitmap);
}

extern "C"
JNIEXPORT jobject JNICALL
Java_org_phcbest_highperformanceimagenative_NativeLib_getImageMinPixelSizePrecisionControl(
        JNIEnv *env, jobject thiz, jobject bitmap, jint precision) {
    return getImageMinPixelSize(env, thiz, bitmap, precision);
}

/**
 * 获得Lut滤镜处理的图片
 */
extern "C"
JNIEXPORT jobject JNICALL
Java_org_phcbest_highperformanceimagenative_NativeLib_getLutFilterImage(
        JNIEnv *env, jobject thiz,
        jobject bitmap,
        jobject lut_bitmap) {
    return getLutFilterImage(env, thiz, bitmap, lut_bitmap);
}