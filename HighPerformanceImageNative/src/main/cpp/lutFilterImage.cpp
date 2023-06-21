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
            // 获取原始像素值
            uint32_t srcPixel = srcPixels[y * bitmapInfo.width + x];

            // 分离原始像素的颜色通道
            uint8_t srcR = (srcPixel >> 16) & 0xff;
            uint8_t srcG = (srcPixel >> 8) & 0xff;
            uint8_t srcB = srcPixel & 0xff;
            // 将颜色通道值转换为百分比
            float srcRPercent = (float) srcR / 255.0f;
            float srcGPercent = (float) srcG / 255.0f;
            float srcBPercent = (float) srcB / 255.0f;
            // 计算在 LUT 表中的位置
            int rPos = static_cast<int>(srcRPercent * 7);
            int gPos = static_cast<int>(srcGPercent * 7);
            int bPos = static_cast<int>(srcBPercent * 7);

            uint32_t pos = bPos * 512 * 512 + gPos * 512 + rPos;

            // 在 LUT 表中查找对应的像素值
            uint32_t lutPixel = lutPixels[pos];

            // 将处理后的像素值存储到新的 Bitmap 对象中
            uint32_t dstPixel =
                    (srcPixel & 0xff000000) | (lutPixel << 16) | (lutPixel << 8) | lutPixel;
            dstPixels[y * bitmapInfo.width + x] = dstPixel;
        }
    }

    // 解锁 Bitmap 对象的像素数组
    AndroidBitmap_unlockPixels(pEnv, bitmap);
    AndroidBitmap_unlockPixels(pEnv, lut_bitmap);
    AndroidBitmap_unlockPixels(pEnv, newBitmap);

    return newBitmap;
}

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
