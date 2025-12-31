package com.uiloalxise.utils;

import com.uiloalxise.ImageData;
import com.uiloalxise.utils.base.ImageObfuscation;

/**
 * @author Uiloalxise
 * @ClassName XuanObfuscation
 * @Description TODO
 */
public class XuanObfuscation extends ImageObfuscation {
    /**
     * 从 ImageData 构造
     *
     * @param imageData 图片数据
     */
    public XuanObfuscation(ImageData imageData) {
        super(imageData);
    }

    /**
     * 从像素数组构造
     *
     * @param pixels 像素数组
     * @param width  宽度
     * @param height 高度
     */
    public XuanObfuscation(int[] pixels, int width, int height) {
        super(pixels, width, height);
    }

    /**
     * 从像素数组构造
     *
     * @param pixels 像素数组
     * @param width  宽度
     * @param height 高度
     * @param format 图片格式后缀
     */
    public XuanObfuscation(int[] pixels, int width, int height, String format) {
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
        return null;
    }
}
