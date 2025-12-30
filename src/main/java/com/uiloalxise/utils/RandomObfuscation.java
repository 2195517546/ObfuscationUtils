package com.uiloalxise.utils;

import com.uiloalxise.ImageData;
import com.uiloalxise.utils.base.ImageObfuscation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Uiloalxise
 * @ClassName RandomObfuscation
 * @Description 随机混淆算法：使用随机种子随机打乱列顺序
 */
public class RandomObfuscation extends ImageObfuscation {

    private final long seed;

    /**
     * 从 ImageData 构造（使用随机种子）
     *
     * @param imageData 图片数据
     */
    public RandomObfuscation(ImageData imageData) {
        this(imageData, System.currentTimeMillis());
    }

    /**
     * 从 ImageData 构造（指定种子）
     *
     * @param imageData 图片数据
     * @param seed 随机种子
     */
    public RandomObfuscation(ImageData imageData, long seed) {
        super(imageData);
        this.seed = seed;
    }

    /**
     * 从像素数组构造（使用随机种子）
     *
     * @param pixels 像素数组
     * @param width 宽度
     * @param height 高度
     */
    public RandomObfuscation(int[] pixels, int width, int height) {
        this(pixels, width, height, System.currentTimeMillis());
    }

    /**
     * 从像素数组构造（指定种子）
     *
     * @param pixels 像素数组
     * @param width 宽度
     * @param height 高度
     * @param seed 随机种子
     */
    public RandomObfuscation(int[] pixels, int width, int height, long seed) {
        super(pixels, width, height);
        this.seed = seed;
    }

    /**
     * 从像素数组构造（指定种子和格式）
     *
     * @param pixels 像素数组
     * @param width 宽度
     * @param height 高度
     * @param format 图片格式后缀
     * @param seed 随机种子
     */
    public RandomObfuscation(int[] pixels, int width, int height, String format, long seed) {
        super(pixels, width, height, format);
        this.seed = seed;
    }

    /**
     * 通用处理方法
     *
     * @param processType 处理类型（加密或解密）
     * @return 处理后的图片
     */
    @Override
    public ImageData process(ProcessType processType) {
        // 创建新的像素数组
        int[] newPixels = new int[pixelCount];
        System.arraycopy(pixels, 0, newPixels, 0, pixelCount);

        if (processType == ProcessType.ENCRYPT) {
            encryptPixels(newPixels);
        } else {
            decryptPixels(newPixels);
        }

        return new ImageData(newPixels, width, height, format);
    }

    /**
     * 加密像素数组
     */
    private void encryptPixels(int[] pixelArray) {
        if (height == 0 || width == 0) {
            return;
        }

        // 检查图像是否有足够的高度来存储密码信息（至少需要2行）
        if (height < 2) {
            throw new IllegalArgumentException("图像高度太小，无法存储列映射信息");
        }

        // 生成随机的列顺序
        int[] randomOrder = generateRandomOrder();

        // 根据随机顺序重排除最后一行外的所有行
        rearrangeColumnsExceptLastRow(pixelArray, randomOrder);

        // 在最后一行保存列映射信息
        saveColumnMappingToLastRow(pixelArray, randomOrder);
    }

    /**
     * 解密像素数组
     */
    private void decryptPixels(int[] pixelArray) {
        if (height == 0 || width == 0) {
            return;
        }


        // 从最后一行读取列映射信息
        int[] columnMapping = readColumnMappingFromLastRow(pixelArray);

        // 根据列索引映射还原所有列的原始顺序
        restoreColumnsWithMapping(pixelArray, columnMapping);
    }

    /**
     * 生成随机的列顺序
     * @return 随机顺序数组，索引i的值表示原始列i应该移动到的新位置
     */
    private int[] generateRandomOrder() {
        Random random = new Random(seed);

        // 创建列索引列表
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            indices.add(i);
        }

        // 使用指定种子打乱列表
        Collections.shuffle(indices, random);

        // 转换为数组：randomOrder[i]表示第i个新位置对应的原始列索引
        int[] randomOrder = new int[width];
        for (int i = 0; i < width; i++) {
            randomOrder[i] = indices.get(i);
        }

        return randomOrder;
    }

    /**
     * 根据随机顺序重排除最后一行外的所有列
     */
    private void rearrangeColumnsExceptLastRow(int[] pixelArray, int[] randomOrder) {
        int[] tempArray = new int[pixelCount];
        System.arraycopy(pixelArray, 0, tempArray, 0, pixelCount);

        // 按照随机顺序重排每一列（除了最后一行）
        for (int newX = 0; newX < width; newX++) {
            int originalX = randomOrder[newX];

            // 将原始列 originalX 移动到新位置 newX（只处理前 height-1 行）
            for (int y = 0; y < height - 1; y++) {
                int originalIndex = y * width + originalX;
                int newIndex = y * width + newX;
                pixelArray[newIndex] = tempArray[originalIndex];
            }
        }
    }

    /**
     * 将列映射信息保存到最后一行
     * 使用2个字节（16位）存储列索引，保留RGB通道的原始像素数据
     */
    private void saveColumnMappingToLastRow(int[] pixelArray, int[] randomOrder) {
        int lastRowY = height - 1;
        int lastRowStartIndex = lastRowY * width;

        // 保存最后一行未重排的原始像素值
        int[] lastRowOriginal = new int[width];
        System.arraycopy(pixelArray, lastRowStartIndex, lastRowOriginal, 0, width);

        // 在最后一行的每个位置保存该位置对应的原始列索引
        for (int newX = 0; newX < width; newX++) {
            int originalX = randomOrder[newX];

            // 获取这个位置的原始像素值（未重排的）
            int originalPixel = lastRowOriginal[newX];

            // 压缩RGB：从24位压缩到16位（每个通道从8位压缩到5/6/5位）
            int r = (originalPixel >> 16) & 0xFF;
            int g = (originalPixel >> 8) & 0xFF;
            int b = originalPixel & 0xFF;

            // RGB565格式：5位红，6位绿，5位蓝
            int rgb565 = ((r >> 3) << 11) | ((g >> 2) << 5) | (b >> 3);

            // 高16位存储列索引，低16位存储压缩的RGB
            int encodedPixel = ((originalX & 0xFFFF) << 16) | (rgb565 & 0xFFFF);

            pixelArray[lastRowStartIndex + newX] = encodedPixel;
        }
    }

    /**
     * 从最后一行读取列映射信息
     */
    private int[] readColumnMappingFromLastRow(int[] pixelArray) {
        int[] columnMapping = new int[width];
        int lastRowY = height - 1;
        int lastRowStartIndex = lastRowY * width;

        // 从最后一行读取列映射信息
        for (int x = 0; x < width; x++) {
            int encodedPixel = pixelArray[lastRowStartIndex + x];

            // 从高16位解码列索引
            columnMapping[x] = (encodedPixel >> 16) & 0xFFFF;
        }

        return columnMapping;
    }

    /**
     * 根据列映射还原所有列的原始顺序
     */
    private void restoreColumnsWithMapping(int[] pixelArray, int[] columnMapping) {
        int[] tempArray = new int[pixelCount];
        System.arraycopy(pixelArray, 0, tempArray, 0, pixelCount);

        int lastRowY = height - 1;

        // 根据列映射还原每一列
        for (int currentX = 0; currentX < width; currentX++) {
            int originalX = columnMapping[currentX];

            // 将当前列 currentX 还原到原始位置 originalX
            for (int y = 0; y < height; y++) {
                int currentIndex = y * width + currentX;
                int originalIndex = y * width + originalX;

                // 对于最后一行，需要解码并恢复原始像素值
                if (y == lastRowY) {
                    int encodedPixel = tempArray[currentIndex];

                    // 从低16位解码压缩的RGB565
                    int rgb565 = encodedPixel & 0xFFFF;

                    // 将RGB565解压回RGB888
                    int r = ((rgb565 >> 11) & 0x1F) << 3; // 5位红扩展到8位
                    int g = ((rgb565 >> 5) & 0x3F) << 2;  // 6位绿扩展到8位
                    int b = (rgb565 & 0x1F) << 3;         // 5位蓝扩展到8位

                    // 重建完整的像素值（Alpha设为255）
                    int restoredPixel = 0xFF000000 | (r << 16) | (g << 8) | b;

                    pixelArray[originalIndex] = restoredPixel;
                } else {
                    pixelArray[originalIndex] = tempArray[currentIndex];
                }
            }
        }
    }

    /**
     * 获取使用的种子
     */
    public long getSeed() {
        return seed;
    }
}
