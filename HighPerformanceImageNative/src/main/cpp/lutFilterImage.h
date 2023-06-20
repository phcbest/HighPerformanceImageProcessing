//
// Created by PengHaiChen on 2023/6/20.
//
#include <jni.h>

#ifndef HIGHPERFORMANCEIMAGEPROCESSING_LUTFILTERIMAGE_H
#define HIGHPERFORMANCEIMAGEPROCESSING_LUTFILTERIMAGE_H

jobject getLutFilterImage(JNIEnv *pEnv, jobject thiz, jobject bitmap, jobject lut_bitmap);


#endif //HIGHPERFORMANCEIMAGEPROCESSING_LUTFILTERIMAGE_H
