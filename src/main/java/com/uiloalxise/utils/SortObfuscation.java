package com.uiloalxise.utils;

import com.uiloalxise.ImageData;
import com.uiloalxise.utils.base.ImageObfuscation;

import java.util.Arrays;

/**
 * @author Uiloalxise
 * @ClassName SortObfuscation
 * @Description 排序混淆算法：根据第一行像素排序后的顺序来重排整个图片的列
 */
public class SortObfuscation extends ImageObfuscation {

    /**
     * 从 ImageData 构造
     *
     * @param imageData 图片数据
     */
    public SortObfuscation(ImageData imageData) {
        super(imageData);
    }

    /**
     * 从像素数组构造
     *
     * @param pixels 像素数组
     * @param width 宽度
     * @param height 高度
     */
    public SortObfuscation(int[] pixels, int width, int height) {
        super(pixels, width, height);
    }

    /**
     * 从像素数组构造
     *
     * @param pixels 像素数组
     * @param width 宽度
     * @param height 高度
     * @param format 图片格式后缀
     */
    public SortObfuscation(int[] pixels, int width, int height, String format) {
        super(pixels, width, height, format);
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

        // 获取第一行的像素和它们的原始索引
        IndexedPixel[] firstRow = new IndexedPixel[width];
        for (int x = 0; x < width; x++) {
            firstRow[x] = new IndexedPixel(pixelArray[x], x);
        }

        // 对第一行进行排序（按像素值）
        Arrays.sort(firstRow, (a, b) -> Integer.compare(a.pixelValue, b.pixelValue));

        // 根据排序后的列顺序重排除最后一行外的所有行
        rearrangeColumnsExceptLastRow(pixelArray, firstRow);

        // 在最后一行保存列映射信息（直接使用列索引值）
        saveColumnMappingToLastRow(pixelArray, firstRow);
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

        // 根据列索引映射还原所有列的原始顺序（包括最后一行）
        restoreColumnsWithMapping(pixelArray, columnMapping);
    }

    /**
     * 根据第一行排序后的顺序重排除最后一行外的所有列
     */
    private void rearrangeColumnsExceptLastRow(int[] pixelArray, IndexedPixel[] sortedFirstRow) {
        int[] tempArray = new int[pixelCount];
        System.arraycopy(pixelArray, 0, tempArray, 0, pixelCount);

        // 按照排序后的列顺序重排每一列（除了最后一行）
        for (int newX = 0; newX < width; newX++) {
            int originalX = sortedFirstRow[newX].originalIndex;

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
    private void saveColumnMappingToLastRow(int[] pixelArray, IndexedPixel[] sortedFirstRow) {
        int lastRowStartIndex = (height - 1) * width;

        // 保存最后一行重排后的原始像素值
        int[] lastRowAfterRearrange = new int[width];
        System.arraycopy(pixelArray, lastRowStartIndex, lastRowAfterRearrange, 0, width);

        // 在最后一行的每个位置保存该位置对应的原始列索引
        for (int newX = 0; newX < width; newX++) {
            int originalX = sortedFirstRow[newX].originalIndex;

            // 获取重排后这个位置的原始像素值
            int originalPixel = lastRowAfterRearrange[newX];

            // 保留RGB通道（低24位），在高8位（Alpha通道）的低16位编码列索引
            // 由于我们只有8位Alpha通道，我们需要另一种编码方式
            // 新方案：使用完整的32位，高16位存储列索引，低16位存储原始像素的RGB压缩值

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
        int lastRowStartIndex = (height - 1) * width;

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


        // 根据列映射还原每一列
        for (int currentX = 0; currentX < width; currentX++) {
            int originalX = columnMapping[currentX];

            // 将当前列 currentX 还原到原始位置 originalX
            for (int y = 0; y < height; y++) {
                int currentIndex = y * width + currentX;
                int originalIndex = y * width + originalX;

                // 对于最后一行，需要解码并恢复原始像素值
                if (y == height - 1) {
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
     * 内部类：包含像素值和原始索引的结构
     */
    private static class IndexedPixel {
        final int pixelValue;
        final int originalIndex;

        IndexedPixel(int pixelValue, int originalIndex) {
            this.pixelValue = pixelValue;
            this.originalIndex = originalIndex;
        }
    }
}
