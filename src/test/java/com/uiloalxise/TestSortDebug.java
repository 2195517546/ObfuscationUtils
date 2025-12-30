package com.uiloalxise;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 调试SortObfuscation算法
 */
public class TestSortDebug {

    @Test
    public void testSimpleSort() {
        // 创建一个简单的3x3图像
        int width = 3;
        int height = 3;
        int[] pixels = {
            5, 4, 3,  // 第一行: 5, 4, 3 -> 排序后: 3(idx 2), 4(idx 1), 5(idx 0)
            7, 8, 9,  // 第二行: 7, 8, 9
            1, 2, 6   // 第三行(最后一行): 1, 2, 6
        };

        System.out.println("原始图像:");
        printImage(pixels, width, height);

        ImageData original = new ImageData(pixels.clone(), width, height);

        // 加密
        ImageData encrypted = Util.sortEncrypt(original);
        System.out.println("\n加密后的图像:");
        printImage(encrypted.getPixels(), width, height);

        // 解密
        ImageData decrypted = Util.sortDecrypt(encrypted);
        System.out.println("\n解密后的图像:");
        printImage(decrypted.getPixels(), width, height);

        // 检查前两行是否一致（最后一行可能有精度损失）
        System.out.println("\n检查前两行:");
        for (int i = 0; i < width * (height - 1); i++) {
            int expected = pixels[i];
            int actual = decrypted.getPixels()[i];
            if (expected != actual) {
                System.out.println("索引 " + i + " 不匹配: 期望=" + expected + ", 实际=" + actual);
            }
        }

        // 检查最后一行（可能有RGB565压缩导致的精度损失）
        System.out.println("\n检查最后一行:");
        int lastRowStart = width * (height - 1);
        for (int i = 0; i < width; i++) {
            int expected = pixels[lastRowStart + i];
            int actual = decrypted.getPixels()[lastRowStart + i];
            System.out.println("最后一行索引 " + i + ": 期望=" + expected + ", 实际=" + actual);
        }
    }

    private void printImage(int[] pixels, int width, int height) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(pixels[y * width + x] + " ");
            }
            System.out.println();
        }
    }
}

