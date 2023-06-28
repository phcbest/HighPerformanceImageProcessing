//
// Created by PengHaiChen on 2023/6/20.
//

#include "lutFilterImage.h"
#include <jni.h>
#include <android/bitmap.h>

jobject createBitmap(JNIEnv *pEnv, int width, int height);

jobject getLutFilterImage(JNIEnv *pEnv, jobject thiz, jobject bitmap, jobject lut_bitmap) {
    // 获取 Bitmap 对象的信息
    AndroidBitmapInfo bitmapInfo;
    if (AndroidBitmap_getInfo(pEnv, bitmap, &bitmapInfo) < 0) {
        return nullptr;
    }

    // 检查 Bitmap 对象的格式是否为 ARGB_8888
    if (bitmapInfo.format != ANDROID_BITMAP_FORMAT_RGBA_8888) {
        return nullptr;
    }

    // 获取 Bitmap 对象的像素数组
    uint32_t *srcPixels;
    if (AndroidBitmap_lockPixels(pEnv, bitmap, (void **) &srcPixels) < 0) {
        return nullptr;
    }

    // 获取 LUT Bitmap 对象的信息
    AndroidBitmapInfo lutBitmapInfo;
    if (AndroidBitmap_getInfo(pEnv, lut_bitmap, &lutBitmapInfo) < 0) {
        return nullptr;
    }

    // 检查 LUT Bitmap 对象的格式是否为 ARGB_8888
    if (lutBitmapInfo.format != ANDROID_BITMAP_FORMAT_RGBA_8888) {
        return nullptr;
    }

    // 获取 LUT Bitmap 对象的像素数组
    uint32_t *lutPixels;
    if (AndroidBitmap_lockPixels(pEnv, lut_bitmap, (void **) &lutPixels) < 0) {
        return nullptr;
    }

    // 创建一个新的 Bitmap 对象
    jobject newBitmap = createBitmap(pEnv, (int) bitmapInfo.width, (int) bitmapInfo.height);

    // 获取新的 Bitmap 对象的像素数组
    uint32_t *dstPixels;
    if (AndroidBitmap_lockPixels(pEnv, newBitmap, (void **) &dstPixels) < 0) {
        return nullptr;
    }

    // 映射原始像素数组到 LUT 上
    for (int y = 0; y < bitmapInfo.height; y++) {
        for (int x = 0; x < bitmapInfo.width; x++) {
            //计算index
            uint32_t index = y * bitmapInfo.width + x;
            // 获取原始像素值
            uint32_t srcPixel = srcPixels[index];
            // 分离原始像素的颜色通道
            uint8_t srcR = (srcPixel >> 16) & 0xff;
            uint8_t srcG = (srcPixel >> 8) & 0xff;
            uint8_t srcB = srcPixel & 0xff;
            //计算lut的index
            int pointX = 1;
            int pointY = 1;
            int pointZ = 1;
            int lutX = 1;
            int lutY = 1;
            //根据lut的坐标计算lut的Index
            uint32_t lutIndex = lutY + lutBitmapInfo.width + lutX;
            //计算lut的rgb
            uint8_t lutR = lutPixels[lutIndex] >> 16 & 0xff;
            uint8_t lutG = lutPixels[lutIndex] >> 8 & 0xff;
            uint8_t lutB = lutPixels[lutIndex] & 0xff;
            // 将处理后的像素值存储到新的 Bitmap 对象中
            uint32_t dstPixel =
                    (srcPixel & 0xff000000) | (lutR << 16) | (lutG << 8) | lutB;
//            uint32_t dstPixel =
//                    (0xff000000) | (lutR << 16) | (lutG << 8) | lutB;
            dstPixels[index] = dstPixel;
        }
    }

    // 解锁 Bitmap 对象的像素数组
    AndroidBitmap_unlockPixels(pEnv, bitmap);
    AndroidBitmap_unlockPixels(pEnv, lut_bitmap);
    AndroidBitmap_unlockPixels(pEnv, newBitmap);

    return newBitmap;
}

/**
 * 创建位图
 * @param pEnv
 * @param width
 * @param height
 * @return
 */
jobject createBitmap(JNIEnv *pEnv, int width, int height) {
    // 获取 Bitmap 类
    jclass bitmapClass = pEnv->FindClass("android/graphics/Bitmap");
    if (bitmapClass == nullptr) {
        return nullptr;
    }

    // 获取 Bitmap.Config 类
    jclass bitmapConfigClass = pEnv->FindClass("android/graphics/Bitmap$Config");
    if (bitmapConfigClass == nullptr) {
        return nullptr;
    }

    // 获取 Bitmap.Config.ARGB_8888 常量
    jfieldID argb8888Field = pEnv->GetStaticFieldID(bitmapConfigClass, "ARGB_8888",
                                                    "Landroid/graphics/Bitmap$Config;");
    if (argb8888Field == nullptr) {
        return nullptr;
    }
    jobject argb8888 = pEnv->GetStaticObjectField(bitmapConfigClass, argb8888Field);

    // 获取 Bitmap.createBitmap() 方法
    jmethodID createBitmapMethod = pEnv->GetStaticMethodID(bitmapClass, "createBitmap",
                                                           "(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;");
    if (createBitmapMethod == nullptr) {
        return nullptr;
    }

    // 调用 Bitmap.createBitmap() 方法创建一个新的 Bitmap 对象
    return pEnv->CallStaticObjectMethod(bitmapClass, createBitmapMethod, width, height, argb8888);
}
