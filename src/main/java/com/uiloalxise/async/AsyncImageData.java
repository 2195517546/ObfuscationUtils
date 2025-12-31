package com.uiloalxise.async;

import com.uiloalxise.ImageData;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * @author Uiloalxise
 * @ClassName AsyncImageData
 * @Description ImageData 的异步操作封装类
 */
public class AsyncImageData {

    /**
     * 异步从文件加载图片
     *
     * @param filePath 文件路径
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> fromFileAsync(String filePath) {
        return fromFileAsync(filePath, ObfuscationExecutor.getExecutor());
    }

    /**
     * 异步从文件加载图片（使用自定义线程池）
     *
     * @param filePath 文件路径
     * @param executor 自定义线程池
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> fromFileAsync(String filePath, ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return ImageData.fromFile(filePath);
            } catch (IOException e) {
                throw new RuntimeException("异步加载图片失败: " + filePath, e);
            }
        }, executor);
    }

    /**
     * 异步从 InputStream 加载图片
     *
     * @param inputStream 输入流
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> fromInputStreamAsync(InputStream inputStream) {
        return fromInputStreamAsync(inputStream, "png", ObfuscationExecutor.getExecutor());
    }

    /**
     * 异步从 InputStream 加载图片，指定格式
     *
     * @param inputStream 输入流
     * @param format 图片格式
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> fromInputStreamAsync(InputStream inputStream, String format) {
        return fromInputStreamAsync(inputStream, format, ObfuscationExecutor.getExecutor());
    }

    /**
     * 异步从 InputStream 加载图片（使用自定义线程池）
     *
     * @param inputStream 输入流
     * @param format 图片格式
     * @param executor 自定义线程池
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> fromInputStreamAsync(InputStream inputStream, String format, ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return ImageData.fromInputStream(inputStream, format);
            } catch (IOException e) {
                throw new RuntimeException("异步从输入流加载图片失败", e);
            }
        }, executor);
    }

    /**
     * 异步从字节数组加载图片
     *
     * @param bytes 图片字节数组
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> fromBytesAsync(byte[] bytes) {
        return fromBytesAsync(bytes, "png", ObfuscationExecutor.getExecutor());
    }

    /**
     * 异步从字节数组加载图片，指定格式
     *
     * @param bytes 图片字节数组
     * @param format 图片格式
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> fromBytesAsync(byte[] bytes, String format) {
        return fromBytesAsync(bytes, format, ObfuscationExecutor.getExecutor());
    }

    /**
     * 异步从字节数组加载图片（使用自定义线程池）
     *
     * @param bytes 图片字节数组
     * @param format 图片格式
     * @param executor 自定义线程池
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> fromBytesAsync(byte[] bytes, String format, ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return ImageData.fromBytes(bytes, format);
            } catch (IOException e) {
                throw new RuntimeException("异步从字节数组加载图片失败", e);
            }
        }, executor);
    }

    /**
     * 异步从 Spring Boot MultipartFile 加载图片
     *
     * @param multipartFile MultipartFile 对象
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> fromMultipartFileAsync(Object multipartFile) {
        return fromMultipartFileAsync(multipartFile, ObfuscationExecutor.getExecutor());
    }

    /**
     * 异步从 Spring Boot MultipartFile 加载图片（使用自定义线程池）
     *
     * @param multipartFile MultipartFile 对象
     * @param executor 自定义线程池
     * @return CompletableFuture<ImageData>
     */
    public static CompletableFuture<ImageData> fromMultipartFileAsync(Object multipartFile, ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return ImageData.fromMultipartFile(multipartFile);
            } catch (IOException e) {
                throw new RuntimeException("异步从 MultipartFile 加载图片失败", e);
            }
        }, executor);
    }

    /**
     * 异步保存图片到文件
     *
     * @param imageData 图片数据
     * @param filePath 文件路径
     * @return CompletableFuture<Void>
     */
    public static CompletableFuture<Void> saveToFileAsync(ImageData imageData, String filePath) {
        return saveToFileAsync(imageData, filePath, ObfuscationExecutor.getExecutor());
    }

    /**
     * 异步保存图片到文件（使用自定义线程池）
     *
     * @param imageData 图片数据
     * @param filePath 文件路径
     * @param executor 自定义线程池
     * @return CompletableFuture<Void>
     */
    public static CompletableFuture<Void> saveToFileAsync(ImageData imageData, String filePath, ExecutorService executor) {
        return CompletableFuture.runAsync(() -> {
            try {
                imageData.saveToFile(filePath);
            } catch (IOException e) {
                throw new RuntimeException("异步保存图片失败: " + filePath, e);
            }
        }, executor);
    }

    /**
     * 异步转换为字节数组
     *
     * @param imageData 图片数据
     * @return CompletableFuture&lt;byte[]&gt;
     */
    public static CompletableFuture<byte[]> toBytesAsync(ImageData imageData) {
        return toBytesAsync(imageData, imageData.getFormat(), ObfuscationExecutor.getExecutor());
    }

    /**
     * 异步转换为字节数组，指定格式
     *
     * @param imageData 图片数据
     * @param format 图片格式
     * @return CompletableFuture&lt;byte[]&gt;
     */
    public static CompletableFuture<byte[]> toBytesAsync(ImageData imageData, String format) {
        return toBytesAsync(imageData, format, ObfuscationExecutor.getExecutor());
    }

    /**
     * 异步转换为字节数组（使用自定义线程池）
     *
     * @param imageData 图片数据
     * @param format 图片格式
     * @param executor 自定义线程池
     * @return CompletableFuture&lt;byte[]&gt;
     */
    public static CompletableFuture<byte[]> toBytesAsync(ImageData imageData, String format, ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return imageData.toBytes(format);
            } catch (IOException e) {
                throw new RuntimeException("异步转换为字节数组失败", e);
            }
        }, executor);
    }

    /**
     * 异步转换为 InputStream
     *
     * @param imageData 图片数据
     * @return CompletableFuture<InputStream>
     */
    public static CompletableFuture<InputStream> toInputStreamAsync(ImageData imageData) {
        return toInputStreamAsync(imageData, imageData.getFormat(), ObfuscationExecutor.getExecutor());
    }

    /**
     * 异步转换为 InputStream，指定格式
     *
     * @param imageData 图片数据
     * @param format 图片格式
     * @return CompletableFuture<InputStream>
     */
    public static CompletableFuture<InputStream> toInputStreamAsync(ImageData imageData, String format) {
        return toInputStreamAsync(imageData, format, ObfuscationExecutor.getExecutor());
    }

    /**
     * 异步转换为 InputStream（使用自定义线程池）
     *
     * @param imageData 图片数据
     * @param format 图片格式
     * @param executor 自定义线程池
     * @return CompletableFuture<InputStream>
     */
    public static CompletableFuture<InputStream> toInputStreamAsync(ImageData imageData, String format, ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return imageData.toInputStream(format);
            } catch (IOException e) {
                throw new RuntimeException("异步转换为输入流失败", e);
            }
        }, executor);
    }
}

