//
// Created by PengHaiChen on 2023/6/17.
//
#include <jni.h>
#include <string>
#include <cstdint>
#include <android/bitmap.h>
#include "imageMinPixelSize.h"

#define max(a, b) ((a) > (b) ? (a) : (b))
#define min(a, b) ((a) < (b) ? (a) : (b))

jobject getImageMinPixelSize(JNIEnv *env, jobject pJobject, jobject bitmap) {
    //rect java类
    jclass rectClass = env->FindClass("android/graphics/Rect");
    //无参ID
    jmethodID id = env->GetMethodID(rectClass, "<init>", "()V");
    //Rect对象
    jobject rect = env->NewObject(rectClass, id);

    AndroidBitmapInfo info;
    //获得bitmap的信息
    AndroidBitmap_getInfo(env, bitmap, &info);
    //快乐位图在这里↓
    void *bitmapPixels;
    //🔒内存
    uint32_t width = info.width;
    uint32_t height = info.height;
    AndroidBitmap_lockPixels(env, bitmap, &bitmapPixels);
    int left = (int32_t) width;
    int top = (int32_t) height;
    int right = 0;
    int bottom = 0;

    if (info.format == ANDROID_BITMAP_FORMAT_A_8) { //是透明通道图
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                void *pixel = ((uint8_t *) bitmapPixels) + y * width + x;//计算像素的指针位置
                uint8_t v = *((uint8_t *) pixel); //指针转数值
                if (v > 0) {
                    left = min(left, x);
                    top = min(top, y);
                    right = max(right, x);
                    bottom = max(bottom, y);
                }
            }
        }
    } else if (info.format == ANDROID_BITMAP_FORMAT_RGBA_8888) {//是PNG图
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                void *pixel = ((uint32_t *) bitmapPixels) + y * width + x;//计算像素的指针位置
                uint32_t v = *((uint32_t *) pixel); //指针转数值
                int alpha = ((int32_t) v >> 24) & 0xff;
                if (alpha > 0) {
                    left = min(left, x);
                    top = min(top, y);
                    right = max(right, x);
                    bottom = max(bottom, y);
                }
            }
        }
    } else {
        left = 0;
        top = 0;
        right = (int32_t) width;
        bottom = (int32_t) height;
    }
    if (left == (int32_t) width && top == (int32_t) height && right == 0 && bottom == 0) {
        left = 0;
        top = 0;
        right = (int32_t) width;
        bottom = (int32_t) height;
    }
    //解🔒内存
    AndroidBitmap_unlockPixels(env, bitmap);

    env->CallVoidMethod(rect, env->GetMethodID(rectClass, "set", "(IIII)V"), left, top, right,
                        bottom);
    return rect;
}


jobject getImageMinPixelSize(JNIEnv *env, jobject pJobject, jobject bitmap, jint precision) {
    jclass rectClass = env->FindClass("android/graphics/Rect");
    jmethodID id = env->GetMethodID(rectClass, "<init>", "()V");
    jobject rect = env->NewObject(rectClass, id);

    AndroidBitmapInfo info;
    AndroidBitmap_getInfo(env, bitmap, &info);
    void *bitmapPixels;
    uint32_t width = info.width;
    uint32_t height = info.height;
    AndroidBitmap_lockPixels(env, bitmap, &bitmapPixels);
    int left = (int32_t) width;
    int top = (int32_t) height;
    int right = 0;
    int bottom = 0;

    if (info.format == ANDROID_BITMAP_FORMAT_A_8) { //是透明通道图
        for (int y = 0; y < height; y += precision) {
            for (int x = 0; x < width; x += precision) {
                void *pixel = ((uint8_t *) bitmapPixels) + y * width + x;//计算像素的指针位置
                uint8_t v = *((uint8_t *) pixel); //指针转数值
                if (v > 0) {
                    left = min(left, x);
                    top = min(top, y);
                    right = max(right, x);
                    bottom = max(bottom, y);
                }
            }
        }
    } else if (info.format == ANDROID_BITMAP_FORMAT_RGBA_8888) {//是PNG图
        for (int y = 0; y < height; y += precision) {
            for (int x = 0; x < width; x += precision) {
                void *pixel = ((uint32_t *) bitmapPixels) + y * width + x;//计算像素的指针位置
                uint32_t v = *((uint32_t *) pixel); //指针转数值
                int alpha = ((int32_t) v >> 24) & 0xff;
                if (alpha > 0) {
                    left = min(left, x);
                    top = min(top, y);
                    right = max(right, x);
                    bottom = max(bottom, y);
                }
            }
        }
    } else {
        left = 0;
        top = 0;
        right = (int32_t) width;
        bottom = (int32_t) height;
    }
    if (left == (int32_t) width && top == (int32_t) height && right == 0 && bottom == 0) {
        left = 0;
        top = 0;
        right = (int32_t) width;
        bottom = (int32_t) height;
    }
    AndroidBitmap_unlockPixels(env, bitmap);

    env->CallVoidMethod(rect, env->GetMethodID(rectClass, "set", "(IIII)V"), left, top, right,
                        bottom);
    return rect;
}