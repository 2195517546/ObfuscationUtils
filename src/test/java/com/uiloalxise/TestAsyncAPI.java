package com.uiloalxise;

import com.uiloalxise.async.AsyncImageData;
import com.uiloalxise.async.AsyncUtil;
import com.uiloalxise.async.ObfuscationExecutor;
import com.uiloalxise.utils.TomatoObfuscation;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Uiloalxise
 * @ClassName TestAsyncAPI
 * @Description 测试异步 API 功能
 */
public class TestAsyncAPI {

    private static final String TEST_IMAGE_PATH = "docs/test.png";
    private static final String OUTPUT_DIR = "docs/out/";

    @AfterAll
    public static void cleanup() {
        // 测试完成后关闭线程池
        ObfuscationExecutor.shutdown();
    }

    @Test
    public void testAsyncImageLoad() throws ExecutionException, InterruptedException {
        System.out.println("\n========================================");
        System.out.println("测试异步图片加载");
        System.out.println("========================================");

        CompletableFuture<ImageData> future = AsyncImageData.fromFileAsync(TEST_IMAGE_PATH);
        assertNotNull(future);

        ImageData imageData = future.get();
        assertNotNull(imageData);
        assertTrue(imageData.getWidth() > 0, "图片宽度应该大于0");
        assertTrue(imageData.getHeight() > 0, "图片高度应该大于0");

        System.out.println("✓ 异步加载图片成功");
        System.out.println("  尺寸: " + imageData.getWidth() + "x" + imageData.getHeight());
    }

    @Test
    public void testAsyncUtilEncryptDecrypt() throws Exception {
        System.out.println("\n========================================");
        System.out.println("测试 AsyncUtil 加密解密");
        System.out.println("========================================");

        ImageData original = ImageData.fromFile(TEST_IMAGE_PATH);
        String key = "0.618";

        // 异步加密
        CompletableFuture<ImageData> encryptFuture = AsyncUtil.tomatoEncryptAsync(key, original);
        ImageData encrypted = encryptFuture.get();
        assertNotNull(encrypted);
        System.out.println("✓ 异步加密完成");

        // 异步解密
        CompletableFuture<ImageData> decryptFuture = AsyncUtil.tomatoDecryptAsync(key, encrypted);
        ImageData decrypted = decryptFuture.get();
        assertNotNull(decrypted);
        System.out.println("✓ 异步解密完成");

        // 验证像素一致性
        assertArrayEquals(original.getPixels(), decrypted.getPixels(),
                "异步加密解密后的像素应该与原图一致");
        System.out.println("✓ 像素验证通过");
    }

    @Test
    public void testObjectLevelAsyncAPI() throws Exception {
        System.out.println("\n========================================");
        System.out.println("测试对象级异步 API");
        System.out.println("========================================");

        ImageData original = ImageData.fromFile(TEST_IMAGE_PATH);
        TomatoObfuscation obfuscation = new TomatoObfuscation(original, 0.618);

        // 使用对象的异步方法
        CompletableFuture<ImageData> encryptFuture = obfuscation.encryptAsync();
        ImageData encrypted = encryptFuture.get();
        assertNotNull(encrypted);
        System.out.println("✓ 对象级异步加密完成");

        // 创建新对象进行解密
        TomatoObfuscation decryptObfuscation = new TomatoObfuscation(encrypted, 0.618);
        CompletableFuture<ImageData> decryptFuture = decryptObfuscation.decryptAsync();
        ImageData decrypted = decryptFuture.get();
        assertNotNull(decrypted);
        System.out.println("✓ 对象级异步解密完成");

        // 验证像素一致性
        assertArrayEquals(original.getPixels(), decrypted.getPixels(),
                "对象级异步加密解密后的像素应该与原图一致");
        System.out.println("✓ 像素验证通过");
    }

    @Test
    public void testAsyncPipeline() throws Exception {
        System.out.println("\n========================================");
        System.out.println("测试异步管道操作");
        System.out.println("========================================");

        ImageData original = ImageData.fromFile(TEST_IMAGE_PATH);
        String outputPath = OUTPUT_DIR + "async_pipeline_test.png";

        // 构建异步管道：加载 -> 加密 -> 保存 -> 解密 -> 保存
        CompletableFuture<Void> pipeline = AsyncImageData.fromFileAsync(TEST_IMAGE_PATH)
                .thenApply(imageData -> {
                    System.out.println("  步骤 1: 图片加载完成");
                    return imageData;
                })
                .thenCompose(imageData -> {
                    System.out.println("  步骤 2: 开始异步加密...");
                    return AsyncUtil.tomatoEncryptAsync("0.618", imageData);
                })
                .thenCompose(encrypted -> {
                    System.out.println("  步骤 3: 加密完成，保存文件...");
                    return AsyncImageData.saveToFileAsync(encrypted, OUTPUT_DIR + "async_encrypted.png")
                            .thenApply(v -> encrypted);
                })
                .thenCompose(encrypted -> {
                    System.out.println("  步骤 4: 开始异步解密...");
                    return AsyncUtil.tomatoDecryptAsync("0.618", encrypted);
                })
                .thenCompose(decrypted -> {
                    System.out.println("  步骤 5: 解密完成，保存文件...");
                    return AsyncImageData.saveToFileAsync(decrypted, OUTPUT_DIR + "async_decrypted.png")
                            .thenApply(v -> decrypted);
                })
                .thenAccept(decrypted -> {
                    System.out.println("  步骤 6: 验证像素...");
                    assertArrayEquals(original.getPixels(), decrypted.getPixels(),
                            "管道处理后的像素应该与原图一致");
                    System.out.println("✓ 异步管道操作完成");
                })
                .exceptionally(ex -> {
                    System.err.println("✗ 管道处理失败: " + ex.getMessage());
                    ex.printStackTrace();
                    fail("管道处理失败: " + ex.getMessage());
                    return null;
                });

        // 等待管道完成
        pipeline.join();
    }

    @Test
    public void testCustomExecutor() throws Exception {
        System.out.println("\n========================================");
        System.out.println("测试自定义线程池");
        System.out.println("========================================");

        // 创建自定义线程池
        var customExecutor = ObfuscationExecutor.createExecutor(4);

        ImageData original = ImageData.fromFile(TEST_IMAGE_PATH);

        // 使用自定义线程池进行异步操作
        CompletableFuture<ImageData> future = AsyncUtil.tomatoEncryptAsync("0.618", original, customExecutor);
        ImageData encrypted = future.get();
        assertNotNull(encrypted);

        System.out.println("✓ 自定义线程池测试通过");

        // 关闭自定义线程池
        customExecutor.shutdown();
    }

    @Test
    public void testAsyncExceptionHandling() throws Exception {
        System.out.println("\n========================================");
        System.out.println("测试异步异常处理");
        System.out.println("========================================");

        // 测试加载不存在的文件
        CompletableFuture<ImageData> future = AsyncImageData.fromFileAsync("nonexistent.png")
                .exceptionally(ex -> {
                    System.out.println("✓ 成功捕获异常: " + ex.getMessage());
                    return null;
                });

        ImageData result = future.get();
        assertNull(result, "应该返回 null");

        System.out.println("✓ 异步异常处理测试通过");
    }
}

