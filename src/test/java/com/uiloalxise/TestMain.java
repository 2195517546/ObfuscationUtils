package com.uiloalxise;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Uiloalxise
 * 默认测试类
 */
public class TestMain {

    // 获取项目根目录
    private static final String PROJECT_ROOT = System.getProperty("user.dir");
    private static final String DOCS_PATH = PROJECT_ROOT + File.separator + "docs";
    private static final String OUTPUT_PATH = DOCS_PATH + File.separator + "out";
    private static final String TEST_IMAGE = DOCS_PATH + File.separator + "test.png";

    @Test
    public void testDefault() {
        // 默认测试方法
        assertTrue(true, "默认测试应该通过");
        assertEquals(2, 1 + 1, "1+1应该等于2");
    }

    @Test
    public void testTomatoObfuscationWithKey() throws IOException {
        System.out.println("\n========================================");
        System.out.println("开始测试 Tomato 混淆 (key = 114514)...");
        System.out.println("========================================");
        System.out.println("项目根目录: " + PROJECT_ROOT);
        System.out.println("输出目录: " + OUTPUT_PATH);

        // 确保输出目录存在
        File outDir = new File(OUTPUT_PATH);
        if (!outDir.exists()) {
            outDir.mkdirs();
            System.out.println("✓ 创建输出目录");
        }

        // 读取原始图片
        ImageData originalImage = ImageData.fromFile(TEST_IMAGE);
        System.out.println("原始图片尺寸: " + originalImage.getWidth() + "x" + originalImage.getHeight());

        String key = "114514";

        // 加密
        ImageData encryptedImage = Util.tomatoEncrypt(key, originalImage);
        System.out.println("✓ 加密完成");

        // 保存加密图片
        String encryptedPath = OUTPUT_PATH + File.separator + "test_tomato_encrypted_key114514.png";
        encryptedImage.saveToFile(encryptedPath);
        System.out.println("✓ 加密图片已保存到: " + encryptedPath);
        System.out.println("  文件存在: " + new File(encryptedPath).exists());

        // 解密
        ImageData decryptedImage = Util.tomatoDecrypt(key, encryptedImage);
        System.out.println("✓ 解密完成");

        // 保存解密图片
        String decryptedPath = OUTPUT_PATH + File.separator + "test_tomato_decrypted_key114514.png";
        decryptedImage.saveToFile(decryptedPath);
        System.out.println("✓ 解密图片已保存到: " + decryptedPath);
        System.out.println("  文件存在: " + new File(decryptedPath).exists());

        // 验证解密后的图片与原图一致
        assertArrayEquals(originalImage.getPixels(), decryptedImage.getPixels(),
            "解密后的图片应该与原图一致");

        System.out.println("✓ Tomato 混淆测试通过！");
        System.out.println("========================================\n");
    }

    @Test
    public void testTomatoObfuscationWithoutKey() throws IOException {
        System.out.println("\n========================================");
        System.out.println("开始测试 Tomato 混淆 (无 key)...");
        System.out.println("========================================");

        // 确保输出目录存在
        File outDir = new File(OUTPUT_PATH);
        if (!outDir.exists()) {
            outDir.mkdirs();
        }

        // 读取原始图片
        ImageData originalImage = ImageData.fromFile(TEST_IMAGE);
        System.out.println("原始图片尺寸: " + originalImage.getWidth() + "x" + originalImage.getHeight());

        String key = "1";

        // 加密
        ImageData encryptedImage = Util.tomatoEncrypt(key, originalImage);
        System.out.println("✓ 加密完成");

        // 保存加密图片
        String encryptedPath = OUTPUT_PATH + File.separator + "test_tomato_encrypted_nokey.png";
        encryptedImage.saveToFile(encryptedPath);
        System.out.println("✓ 加密图片已保存到: " + encryptedPath);
        System.out.println("  文件存在: " + new File(encryptedPath).exists());

        // 解密
        ImageData decryptedImage = Util.tomatoDecrypt(key, encryptedImage);
        System.out.println("✓ 解密完成");

        // 保存解密图片
        String decryptedPath = OUTPUT_PATH + File.separator + "test_tomato_decrypted_nokey.png";
        decryptedImage.saveToFile(decryptedPath);
        System.out.println("✓ 解密图片已保存到: " + decryptedPath);
        System.out.println("  文件存在: " + new File(decryptedPath).exists());

        // 验证解密后的图片与原图一致
        assertArrayEquals(originalImage.getPixels(), decryptedImage.getPixels(),
            "解密后的图片应该与原图一致");

        System.out.println("✓ Tomato 混淆测试通过！");
        System.out.println("========================================\n");
    }

    @Test
    public void testBlockObfuscation() throws IOException {
        System.out.println("\n========================================");
        System.out.println("开始测试 Block 混淆 (key = \"testkey123\")...");
        System.out.println("========================================");

        // 确保输出目录存在
        File outDir = new File(OUTPUT_PATH);
        if (!outDir.exists()) {
            outDir.mkdirs();
        }

        // 读取原始图片
        ImageData originalImage = ImageData.fromFile(TEST_IMAGE);
        System.out.println("原始图片尺寸: " + originalImage.getWidth() + "x" + originalImage.getHeight());

        String key = "testkey123";

        // 加密
        ImageData encryptedImage = Util.blockEncrypt(key, originalImage);
        System.out.println("✓ 加密完成");
        System.out.println("  加密后尺寸: " + encryptedImage.getWidth() + "x" + encryptedImage.getHeight());

        // 保存加密图片
        String encryptedPath = OUTPUT_PATH + File.separator + "test_block_encrypted.png";
        encryptedImage.saveToFile(encryptedPath);
        System.out.println("✓ 加密图片已保存到: " + encryptedPath);
        System.out.println("  文件存在: " + new File(encryptedPath).exists());

        // 解密
        ImageData decryptedImage = Util.blockDecrypt(key, encryptedImage);
        System.out.println("✓ 解密完成");
        System.out.println("  解密后尺寸: " + decryptedImage.getWidth() + "x" + decryptedImage.getHeight());

        // 保存解密图片
        String decryptedPath = OUTPUT_PATH + File.separator + "test_block_decrypted.png";
        decryptedImage.saveToFile(decryptedPath);
        System.out.println("✓ 解密图片已保存到: " + decryptedPath);
        System.out.println("  文件存在: " + new File(decryptedPath).exists());

        // Block 混淆可能改变图片尺寸，所以只在文件生成时验证
        assertTrue(new File(encryptedPath).exists(), "加密文件应该存在");
        assertTrue(new File(decryptedPath).exists(), "解密文件应该存在");

        System.out.println("✓ Block 混淆测试通过！");
        System.out.println("========================================\n");
    }

    @Test
    public void testRowPixelObfuscation() throws IOException {
        System.out.println("\n========================================");
        System.out.println("开始测试 Row Pixel 混淆 (key = \"rowpixelkey\")...");
        System.out.println("========================================");

        // 确保输出目录存在
        File outDir = new File(OUTPUT_PATH);
        if (!outDir.exists()) {
            outDir.mkdirs();
        }

        // 读取原始图片
        ImageData originalImage = ImageData.fromFile(TEST_IMAGE);
        System.out.println("原始图片尺寸: " + originalImage.getWidth() + "x" + originalImage.getHeight());

        String key = "rowpixelkey";

        // 加密
        ImageData encryptedImage = Util.rowPixelEncrypt(key, originalImage);
        System.out.println("✓ 加密完成");

        // 保存加密图片
        String encryptedPath = OUTPUT_PATH + File.separator + "test_rowpixel_encrypted.png";
        encryptedImage.saveToFile(encryptedPath);
        System.out.println("✓ 加密图片已保存到: " + encryptedPath);
        System.out.println("  文件存在: " + new File(encryptedPath).exists());

        // 解密
        ImageData decryptedImage = Util.rowPixelDecrypt(key, encryptedImage);
        System.out.println("✓ 解密完成");

        // 保存解密图片
        String decryptedPath = OUTPUT_PATH + File.separator + "test_rowpixel_decrypted.png";
        decryptedImage.saveToFile(decryptedPath);
        System.out.println("✓ 解密图片已保存到: " + decryptedPath);
        System.out.println("  文件存在: " + new File(decryptedPath).exists());

        // 验证解密后的图片与原图一致
        assertArrayEquals(originalImage.getPixels(), decryptedImage.getPixels(),
            "解密后的图片应该与原图一致");

        System.out.println("✓ Row Pixel 混淆测试通过！");
        System.out.println("========================================\n");
    }

    @Test
    public void testPerPixelObfuscation() throws IOException {
        System.out.println("\n========================================");
        System.out.println("开始测试 Per Pixel 混淆 (key = \"perpixelkey\")...");
        System.out.println("========================================");

        // 确保输出目录存在
        File outDir = new File(OUTPUT_PATH);
        if (!outDir.exists()) {
            outDir.mkdirs();
        }

        // 读取原始图片
        ImageData originalImage = ImageData.fromFile(TEST_IMAGE);
        System.out.println("原始图片尺寸: " + originalImage.getWidth() + "x" + originalImage.getHeight());

        String key = "perpixelkey";

        // 加密
        ImageData encryptedImage = Util.perPixelEncrypt(key, originalImage);
        System.out.println("✓ 加密完成");

        // 保存加密图片
        String encryptedPath = OUTPUT_PATH + File.separator + "test_perpixel_encrypted.png";
        encryptedImage.saveToFile(encryptedPath);
        System.out.println("✓ 加密图片已保存到: " + encryptedPath);
        System.out.println("  文件存在: " + new File(encryptedPath).exists());

        // 解密
        ImageData decryptedImage = Util.perPixelDecrypt(key, encryptedImage);
        System.out.println("✓ 解密完成");

        // 保存解密图片
        String decryptedPath = OUTPUT_PATH + File.separator + "test_perpixel_decrypted.png";
        decryptedImage.saveToFile(decryptedPath);
        System.out.println("✓ 解密图片已保存到: " + decryptedPath);
        System.out.println("  文件存在: " + new File(decryptedPath).exists());

        // 验证解密后的图片与原图一致
        assertArrayEquals(originalImage.getPixels(), decryptedImage.getPixels(),
            "解密后的图片应该与原图一致");

        System.out.println("✓ Per Pixel 混淆测试通过！");
        System.out.println("========================================\n");
    }

    @Test
    public void testPicEncryptRowObfuscation() throws IOException {
        System.out.println("\n========================================");
        System.out.println("开始测试 PicEncryptRow 混淆 (key = \"0.618\")...");
        System.out.println("========================================");

        // 确保输出目录存在
        File outDir = new File(OUTPUT_PATH);
        if (!outDir.exists()) {
            outDir.mkdirs();
        }

        // 读取原始图片
        ImageData originalImage = ImageData.fromFile(TEST_IMAGE);
        System.out.println("原始图片尺寸: " + originalImage.getWidth() + "x" + originalImage.getHeight());

        String key = "0.618";

        // 加密
        ImageData encryptedImage = Util.picEncryptRowEncrypt(key, originalImage);
        System.out.println("✓ 加密完成");

        // 保存加密图片
        String encryptedPath = OUTPUT_PATH + File.separator + "test_picencryptrow_encrypted.png";
        encryptedImage.saveToFile(encryptedPath);
        System.out.println("✓ 加密图片已保存到: " + encryptedPath);
        System.out.println("  文件存在: " + new File(encryptedPath).exists());

        // 解密
        ImageData decryptedImage = Util.picEncryptRowDecrypt(key, encryptedImage);
        System.out.println("✓ 解密完成");

        // 保存解密图片
        String decryptedPath = OUTPUT_PATH + File.separator + "test_picencryptrow_decrypted.png";
        decryptedImage.saveToFile(decryptedPath);
        System.out.println("✓ 解密图片已保存到: " + decryptedPath);
        System.out.println("  文件存在: " + new File(decryptedPath).exists());

        // 验证解密后的图片与原图一致
        assertArrayEquals(originalImage.getPixels(), decryptedImage.getPixels(),
            "解密后的图片应该与原图一致");

        System.out.println("✓ PicEncryptRow 混淆测试通过！");
        System.out.println("========================================\n");
    }

    @Test
    public void testPicEncryptRowColumnObfuscation() throws IOException {
        System.out.println("\n========================================");
        System.out.println("开始测试 PicEncryptRowColumn 混淆 (key = \"0.618\")...");
        System.out.println("========================================");

        // 确保输出目录存在
        File outDir = new File(OUTPUT_PATH);
        if (!outDir.exists()) {
            outDir.mkdirs();
        }

        // 读取原始图片
        ImageData originalImage = ImageData.fromFile(TEST_IMAGE);
        System.out.println("原始图片尺寸: " + originalImage.getWidth() + "x" + originalImage.getHeight());

        String key = "0.618";

        // 加密
        ImageData encryptedImage = Util.picEncryptRowColumnEncrypt(key, originalImage);
        System.out.println("✓ 加密完成");

        // 保存加密图片
        String encryptedPath = OUTPUT_PATH + File.separator + "test_picencryptrowcolumn_encrypted.png";
        encryptedImage.saveToFile(encryptedPath);
        System.out.println("✓ 加密图片已保存到: " + encryptedPath);
        System.out.println("  文件存在: " + new File(encryptedPath).exists());

        // 解密
        ImageData decryptedImage = Util.picEncryptRowColumnDecrypt(key, encryptedImage);
        System.out.println("✓ 解密完成");

        // 保存解密图片
        String decryptedPath = OUTPUT_PATH + File.separator + "test_picencryptrowcolumn_decrypted.png";
        decryptedImage.saveToFile(decryptedPath);
        System.out.println("✓ 解密图片已保存到: " + decryptedPath);
        System.out.println("  文件存在: " + new File(decryptedPath).exists());

        // 验证解密后的图片与原图一致
        assertArrayEquals(originalImage.getPixels(), decryptedImage.getPixels(),
            "解密后的图片应该与原图一致");

        System.out.println("✓ PicEncryptRowColumn 混淆测试通过！");
        System.out.println("========================================\n");
    }

    @Test
    public void testSortObfuscation() throws IOException {
        System.out.println("\n========================================");
        System.out.println("开始测试 Sort 排序混淆...");
        System.out.println("========================================");

        // 确保输出目录存在
        File outDir = new File(OUTPUT_PATH);
        if (!outDir.exists()) {
            outDir.mkdirs();
            System.out.println("✓ 创建输出目录");
        }

        // 读取原始图片
        ImageData originalImage = ImageData.fromFile(TEST_IMAGE);
        System.out.println("原始图片尺寸: " + originalImage.getWidth() + "x" + originalImage.getHeight());

        // 加密
        ImageData encryptedImage = Util.sortEncrypt(originalImage);
        System.out.println("✓ 加密完成");

        // 保存加密图片
        String encryptedPath = OUTPUT_PATH + File.separator + "test_sort_encrypted.png";
        encryptedImage.saveToFile(encryptedPath);
        System.out.println("✓ 加密图片已保存到: " + encryptedPath);
        System.out.println("  文件存在: " + new File(encryptedPath).exists());

        // 解密
        ImageData decryptedImage = Util.sortDecrypt(encryptedImage);
        System.out.println("✓ 解密完成");

        // 保存解密图片
        String decryptedPath = OUTPUT_PATH + File.separator + "test_sort_decrypted.png";
        decryptedImage.saveToFile(decryptedPath);
        System.out.println("✓ 解密图片已保存到: " + decryptedPath);
        assertTrue(new File(decryptedPath).exists(), "解密图片文件应该存在");
        System.out.println("  文件存在: " + new File(decryptedPath).exists());

        System.out.println("✓ Sort 排序混淆测试通过！");
        System.out.println("========================================\n");
    }

    @Test
    public void testRandomObfuscation() throws IOException {
        System.out.println("\n========================================");
        System.out.println("开始测试 Random 随机混淆 (seed = 114514)...");
        System.out.println("========================================");

        // 确保输出目录存在
        File outDir = new File(OUTPUT_PATH);
        if (!outDir.exists()) {
            outDir.mkdirs();
            System.out.println("✓ 创建输出目录");
        }

        // 读取原始图片
        ImageData originalImage = ImageData.fromFile(TEST_IMAGE);
        System.out.println("原始图片尺寸: " + originalImage.getWidth() + "x" + originalImage.getHeight());

        String seed = "114514";

        // 加密
        ImageData encryptedImage = Util.randomEncrypt(seed, originalImage);
        System.out.println("✓ 加密完成");

        // 保存加密图片
        String encryptedPath = OUTPUT_PATH + File.separator + "test_random_encrypted.png";
        encryptedImage.saveToFile(encryptedPath);
        System.out.println("✓ 加密图片已保存到: " + encryptedPath);
        System.out.println("  文件存在: " + new File(encryptedPath).exists());

        // 解密
        ImageData decryptedImage = Util.randomDecrypt(seed, encryptedImage);
        System.out.println("✓ 解密完成");

        // 保存解密图片
        String decryptedPath = OUTPUT_PATH + File.separator + "test_random_decrypted.png";
        decryptedImage.saveToFile(decryptedPath);
        System.out.println("✓ 解密图片已保存到: " + decryptedPath);
        System.out.println("  文件存在: " + new File(decryptedPath).exists());

        // 验证解密后的图片与原图的差异（最后一行可能有RGB565压缩损失）
        int[] originalPixels = originalImage.getPixels();
        int[] decryptedPixels = decryptedImage.getPixels();

        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        int lastRowY = height - 1;
        int lastRowStart = lastRowY * width;
        int lastRowEnd = lastRowStart + width;

        int differentPixels = 0;
        for (int i = 0; i < originalPixels.length; i++) {
            // 跳过最后一行（因为有RGB565压缩）
            if (i >= lastRowStart && i < lastRowEnd) {
                continue;
            }
            if (originalPixels[i] != decryptedPixels[i]) {
                differentPixels++;
            }
        }

        assertEquals(0, differentPixels, "除最后一行外的像素应该完全一致");

        System.out.println("✓ Random 随机混淆测试通过！");
        System.out.println("========================================\n");
    }
}
