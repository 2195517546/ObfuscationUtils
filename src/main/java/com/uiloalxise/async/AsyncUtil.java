package com.uiloalxise.async;

import com.uiloalxise.ImageData;
import com.uiloalxise.utils.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * @author Uiloalxise
 * @ClassName AsyncUtil
 * @Description 异步混淆工具类，提供所有混淆算法的异步版本
 */
public class AsyncUtil {

    // ==================== Tomato 混淆 ====================

    /**
     * 异步 Tomato 加密
     *
     * @param key 密钥
     * @param imageData 图片数据
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> tomatoEncryptAsync(String key, ImageData imageData) {
        return tomatoEncryptAsync(key, imageData, ObfuscationExecutor.getExecutor());
    }

    /**
     * 异步 Tomato 加密（使用自定义线程池）
     *
     * @param key 密钥
     * @param imageData 图片数据
     * @param executor 自定义线程池
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> tomatoEncryptAsync(String key, ImageData imageData, ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            TomatoObfuscation tomatoObfuscation = new TomatoObfuscation(imageData, Double.parseDouble(key));
            return tomatoObfuscation.encrypt();
        }, executor);
    }

    /**
     * 异步 Tomato 解密
     *
     * @param key 密钥
     * @param imageData 图片数据
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> tomatoDecryptAsync(String key, ImageData imageData) {
        return tomatoDecryptAsync(key, imageData, ObfuscationExecutor.getExecutor());
    }

    /**
     * 异步 Tomato 解密（使用自定义线程池）
     *
     * @param key 密钥
     * @param imageData 图片数据
     * @param executor 自定义线程池
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> tomatoDecryptAsync(String key, ImageData imageData, ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            TomatoObfuscation tomatoObfuscation = new TomatoObfuscation(imageData, Double.parseDouble(key));
            return tomatoObfuscation.decrypt();
        }, executor);
    }

    // ==================== Block 混淆 ====================

    /**
     * 异步 Block 加密
     *
     * @param key 密钥
     * @param imageData 图片数据
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> blockEncryptAsync(String key, ImageData imageData) {
        return blockEncryptAsync(key, imageData, ObfuscationExecutor.getExecutor());
    }

    /**
     * 异步 Block 加密（使用自定义线程池）
     *
     * @param key 密钥
     * @param imageData 图片数据
     * @param executor 自定义线程池
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> blockEncryptAsync(String key, ImageData imageData, ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            BlockObfuscation blockObfuscation = new BlockObfuscation(imageData, key);
            return blockObfuscation.encrypt();
        }, executor);
    }

    /**
     * 异步 Block 解密
     *
     * @param key 密钥
     * @param imageData 图片数据
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> blockDecryptAsync(String key, ImageData imageData) {
        return blockDecryptAsync(key, imageData, ObfuscationExecutor.getExecutor());
    }

    /**
     * 异步 Block 解密（使用自定义线程池）
     *
     * @param key 密钥
     * @param imageData 图片数据
     * @param executor 自定义线程池
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> blockDecryptAsync(String key, ImageData imageData, ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            BlockObfuscation blockObfuscation = new BlockObfuscation(imageData, key);
            return blockObfuscation.decrypt();
        }, executor);
    }

    // ==================== Row Pixel 混淆 ====================

    /**
     * 异步行像素加密
     *
     * @param key 密钥
     * @param imageData 图片数据
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> rowPixelEncryptAsync(String key, ImageData imageData) {
        return rowPixelEncryptAsync(key, imageData, ObfuscationExecutor.getExecutor());
    }

    /**
     * 异步行像素加密（使用自定义线程池）
     *
     * @param key 密钥
     * @param imageData 图片数据
     * @param executor 自定义线程池
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> rowPixelEncryptAsync(String key, ImageData imageData, ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            RowPixelObfuscation rowPixelObfuscation = new RowPixelObfuscation(imageData, key);
            return rowPixelObfuscation.encrypt();
        }, executor);
    }

    /**
     * 异步行像素解密
     *
     * @param key 密钥
     * @param imageData 图片数据
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> rowPixelDecryptAsync(String key, ImageData imageData) {
        return rowPixelDecryptAsync(key, imageData, ObfuscationExecutor.getExecutor());
    }

    /**
     * 异步行像素解密（使用自定义线程池）
     *
     * @param key 密钥
     * @param imageData 图片数据
     * @param executor 自定义线程池
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> rowPixelDecryptAsync(String key, ImageData imageData, ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            RowPixelObfuscation rowPixelObfuscation = new RowPixelObfuscation(imageData, key);
            return rowPixelObfuscation.decrypt();
        }, executor);
    }

    // ==================== Per Pixel 混淆 ====================

    /**
     * 异步像素加密
     *
     * @param key 密钥
     * @param imageData 图片数据
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> perPixelEncryptAsync(String key, ImageData imageData) {
        return perPixelEncryptAsync(key, imageData, ObfuscationExecutor.getExecutor());
    }

    /**
     * 异步像素加密（使用自定义线程池）
     *
     * @param key 密钥
     * @param imageData 图片数据
     * @param executor 自定义线程池
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> perPixelEncryptAsync(String key, ImageData imageData, ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            PerPixelObfuscation perPixelObfuscation = new PerPixelObfuscation(imageData, key);
            return perPixelObfuscation.encrypt();
        }, executor);
    }

    /**
     * 异步像素解密
     *
     * @param key 密钥
     * @param imageData 图片数据
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> perPixelDecryptAsync(String key, ImageData imageData) {
        return perPixelDecryptAsync(key, imageData, ObfuscationExecutor.getExecutor());
    }

    /**
     * 异步像素解密（使用自定义线程池）
     *
     * @param key 密钥
     * @param imageData 图片数据
     * @param executor 自定义线程池
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> perPixelDecryptAsync(String key, ImageData imageData, ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            PerPixelObfuscation perPixelObfuscation = new PerPixelObfuscation(imageData, key);
            return perPixelObfuscation.decrypt();
        }, executor);
    }

    // ==================== PicEncryptRow 混淆 ====================

    /**
     * 异步 PicEncryptRow 加密
     *
     * @param key 密钥
     * @param imageData 图片数据
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> picEncryptRowEncryptAsync(String key, ImageData imageData) {
        return picEncryptRowEncryptAsync(key, imageData, ObfuscationExecutor.getExecutor());
    }

    /**
     * 异步 PicEncryptRow 加密（使用自定义线程池）
     *
     * @param key 密钥
     * @param imageData 图片数据
     * @param executor 自定义线程池
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> picEncryptRowEncryptAsync(String key, ImageData imageData, ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                double keyDouble = Double.parseDouble(key);
                PicEncryptRowObfuscation picEncryptRowObfuscation = new PicEncryptRowObfuscation(imageData, keyDouble);
                return picEncryptRowObfuscation.encrypt();
            } catch (Exception e) {
                PicEncryptRowObfuscation picEncryptRowObfuscation = new PicEncryptRowObfuscation(imageData, 0.114514);
                return picEncryptRowObfuscation.encrypt();
            }
        }, executor);
    }

    /**
     * 异步 PicEncryptRow 解密
     *
     * @param key 密钥
     * @param imageData 图片数据
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> picEncryptRowDecryptAsync(String key, ImageData imageData) {
        return picEncryptRowDecryptAsync(key, imageData, ObfuscationExecutor.getExecutor());
    }

    /**
     * 异步 PicEncryptRow 解密（使用自定义线程池）
     *
     * @param key 密钥
     * @param imageData 图片数据
     * @param executor 自定义线程池
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> picEncryptRowDecryptAsync(String key, ImageData imageData, ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                double keyDouble = Double.parseDouble(key);
                PicEncryptRowObfuscation picEncryptRowObfuscation = new PicEncryptRowObfuscation(imageData, keyDouble);
                return picEncryptRowObfuscation.decrypt();
            } catch (Exception e) {
                PicEncryptRowObfuscation picEncryptRowObfuscation = new PicEncryptRowObfuscation(imageData, 0.114514);
                return picEncryptRowObfuscation.decrypt();
            }
        }, executor);
    }

    // ==================== PicEncryptRowColumn 混淆 ====================

    /**
     * 异步 PicEncryptRowColumn 加密
     *
     * @param key 密钥
     * @param imageData 图片数据
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> picEncryptRowColumnEncryptAsync(String key, ImageData imageData) {
        return picEncryptRowColumnEncryptAsync(key, imageData, ObfuscationExecutor.getExecutor());
    }

    /**
     * 异步 PicEncryptRowColumn 加密（使用自定义线程池）
     *
     * @param key 密钥
     * @param imageData 图片数据
     * @param executor 自定义线程池
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> picEncryptRowColumnEncryptAsync(String key, ImageData imageData, ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                double keyDouble = Double.parseDouble(key);
                PicEncryptRowColumnObfuscation picEncryptRowColumnObfuscation = new PicEncryptRowColumnObfuscation(imageData, keyDouble);
                return picEncryptRowColumnObfuscation.encrypt();
            } catch (Exception e) {
                PicEncryptRowColumnObfuscation picEncryptRowColumnObfuscation = new PicEncryptRowColumnObfuscation(imageData, 0.1145);
                return picEncryptRowColumnObfuscation.encrypt();
            }
        }, executor);
    }

    /**
     * 异步 PicEncryptRowColumn 解密
     *
     * @param key 密钥
     * @param imageData 图片数据
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> picEncryptRowColumnDecryptAsync(String key, ImageData imageData) {
        return picEncryptRowColumnDecryptAsync(key, imageData, ObfuscationExecutor.getExecutor());
    }

    /**
     * 异步 PicEncryptRowColumn 解密（使用自定义线程池）
     *
     * @param key 密钥
     * @param imageData 图片数据
     * @param executor 自定义线程池
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> picEncryptRowColumnDecryptAsync(String key, ImageData imageData, ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                double keyDouble = Double.parseDouble(key);
                PicEncryptRowColumnObfuscation picEncryptRowColumnObfuscation = new PicEncryptRowColumnObfuscation(imageData, keyDouble);
                return picEncryptRowColumnObfuscation.decrypt();
            } catch (Exception e) {
                PicEncryptRowColumnObfuscation picEncryptRowColumnObfuscation = new PicEncryptRowColumnObfuscation(imageData, 0.1145);
                return picEncryptRowColumnObfuscation.decrypt();
            }
        }, executor);
    }

    // ==================== Sort 混淆 ====================

    /**
     * 异步排序混淆加密
     *
     * @param imageData 图片数据
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> sortEncryptAsync(ImageData imageData) {
        return sortEncryptAsync(imageData, ObfuscationExecutor.getExecutor());
    }

    /**
     * 异步排序混淆加密（使用自定义线程池）
     *
     * @param imageData 图片数据
     * @param executor 自定义线程池
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> sortEncryptAsync(ImageData imageData, ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            SortObfuscation sortObfuscation = new SortObfuscation(imageData);
            return sortObfuscation.encrypt();
        }, executor);
    }

    /**
     * 异步排序混淆解密
     *
     * @param imageData 图片数据
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> sortDecryptAsync(ImageData imageData) {
        return sortDecryptAsync(imageData, ObfuscationExecutor.getExecutor());
    }

    /**
     * 异步排序混淆解密（使用自定义线程池）
     *
     * @param imageData 图片数据
     * @param executor 自定义线程池
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> sortDecryptAsync(ImageData imageData, ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            SortObfuscation sortObfuscation = new SortObfuscation(imageData);
            return sortObfuscation.decrypt();
        }, executor);
    }

    // ==================== Random 混淆 ====================

    /**
     * 异步随机混淆加密（使用种子）
     *
     * @param seed 随机种子
     * @param imageData 图片数据
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> randomEncryptAsync(String seed, ImageData imageData) {
        return randomEncryptAsync(seed, imageData, ObfuscationExecutor.getExecutor());
    }

    /**
     * 异步随机混淆加密（使用种子和自定义线程池）
     *
     * @param seed 随机种子
     * @param imageData 图片数据
     * @param executor 自定义线程池
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> randomEncryptAsync(String seed, ImageData imageData, ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                long seedLong = Long.parseLong(seed);
                RandomObfuscation randomObfuscation = new RandomObfuscation(imageData, seedLong);
                return randomObfuscation.encrypt();
            } catch (Exception e) {
                RandomObfuscation randomObfuscation = new RandomObfuscation(imageData, seed.hashCode());
                return randomObfuscation.encrypt();
            }
        }, executor);
    }

    /**
     * 异步随机混淆解密（使用种子）
     *
     * @param seed 随机种子
     * @param imageData 图片数据
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> randomDecryptAsync(String seed, ImageData imageData) {
        return randomDecryptAsync(seed, imageData, ObfuscationExecutor.getExecutor());
    }

    /**
     * 异步随机混淆解密（使用种子和自定义线程池）
     *
     * @param seed 随机种子
     * @param imageData 图片数据
     * @param executor 自定义线程池
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> randomDecryptAsync(String seed, ImageData imageData, ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                long seedLong = Long.parseLong(seed);
                RandomObfuscation randomObfuscation = new RandomObfuscation(imageData, seedLong);
                return randomObfuscation.decrypt();
            } catch (Exception e) {
                RandomObfuscation randomObfuscation = new RandomObfuscation(imageData, seed.hashCode());
                return randomObfuscation.decrypt();
            }
        }, executor);
    }

    /**
     * 异步随机混淆加密（使用当前时间戳作为种子）
     *
     * @param imageData 图片数据
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> randomEncryptAsync(ImageData imageData) {
        return randomEncryptAsync(imageData, ObfuscationExecutor.getExecutor());
    }

    /**
     * 异步随机混淆加密（使用当前时间戳作为种子和自定义线程池）
     *
     * @param imageData 图片数据
     * @param executor 自定义线程池
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> randomEncryptAsync(ImageData imageData, ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            RandomObfuscation randomObfuscation = new RandomObfuscation(imageData);
            return randomObfuscation.encrypt();
        }, executor);
    }
}

