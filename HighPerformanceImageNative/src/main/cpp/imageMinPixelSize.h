//
// Created by PengHaiChen on 2023/6/17.
//
#include <jni.h>

#ifndef HIGHPERFORMANCEIMAGEPROCESSING_IMAGEMINPIXELSIZE_H
#define HIGHPERFORMANCEIMAGEPROCESSING_IMAGEMINPIXELSIZE_H

jobject getImageMinPixelSize(JNIEnv *env, jobject pJobject, jobject bitmap);

jobject getImageMinPixelSize(JNIEnv *pEnv, jobject thiz, jobject pJobject1, jint precision);

#endif //HIGHPERFORMANCEIMAGEPROCESSING_IMAGEMINPIXELSIZE_H
