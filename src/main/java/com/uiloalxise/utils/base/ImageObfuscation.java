package com.uiloalxise.utils.base;

import com.uiloalxise.ImageData;

/**
 * @author Uiloalxise
 * @ClassName ImageObfuscation
 * @Description 图片混淆抽象类，定义图片加密和解密的标准方法
 */
public abstract class ImageObfuscation {

	protected final int[] pixels;
	protected final int width;
	protected final int height;
	protected final int pixelCount;
	protected final String format;

	/**
	 * 处理类型枚举
	 */
	public enum ProcessType {
		/** 加密 */
		ENCRYPT,
		/** 解密 */
		DECRYPT
	}

	/**
	 * 从 ImageData 构造
	 *
	 * @param imageData 图片数据
	 */
	public ImageObfuscation(ImageData imageData) {
		this.pixels = imageData.getPixels();
		this.width = imageData.getWidth();
		this.height = imageData.getHeight();
		this.pixelCount = width * height;
		this.format = imageData.getFormat();
	}

	/**
	 * 从像素数组构造
	 *
	 * @param pixels 像素数组
	 * @param width 宽度
	 * @param height 高度
	 */
	public ImageObfuscation(int[] pixels, int width, int height) {
		this(pixels, width, height, "png");
	}

	/**
	 * 从像素数组构造
	 *
	 * @param pixels 像素数组
	 * @param width 宽度
	 * @param height 高度
	 * @param format 图片格式后缀
	 */
	public ImageObfuscation(int[] pixels, int width, int height, String format) {
		if (pixels == null || pixels.length != width * height) {
			throw new IllegalArgumentException("像素数组长度必须等于 width * height");
		}
		this.pixels = pixels;
		this.width = width;
		this.height = height;
		this.pixelCount = width * height;
		this.format = format != null ? format.toLowerCase() : "png";
	}

	/**
	 * 通用处理方法
	 *
	 * @param processType 处理类型（加密或解密）
	 * @return 处理后的图片
	 */
	public abstract ImageData process(ProcessType processType);

	/**
	 * 加密图片
	 *
	 * @return 加密后的图片
	 */
	public ImageData encrypt() {
		return process(ProcessType.ENCRYPT);
	}

	/**
	 * 解密图片
	 *
	 * @return 解密后的图片
	 */
	public ImageData decrypt() {
		return process(ProcessType.DECRYPT);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getPixelCount() {
		return pixelCount;
	}

	public String getFormat() {
		return format;
	}
}

