#include <jni.h>
#include "imageMinPixelSize.h"

extern "C"
JNIEXPORT jobject JNICALL
Java_org_phcbest_highperformanceimagenative_NativeLib_getImageMinPixelSize(JNIEnv *env,
                                                                           jobject thiz,
                                                                           jobject bitmap) {
    return getImageMinPixelSize(env, thiz, bitmap);
}