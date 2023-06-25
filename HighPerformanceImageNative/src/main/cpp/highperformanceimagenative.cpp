#include <jni.h>
#include "imageMinPixelSize.h"


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
