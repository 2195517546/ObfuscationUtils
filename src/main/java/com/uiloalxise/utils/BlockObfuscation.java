package com.uiloalxise.utils;

import com.uiloalxise.ImageData;
import com.uiloalxise.utils.base.PixelObfuscation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author Uiloalxise
 * @ClassName BlockObfuscation
 * @Description 块混淆算法实现
 */
public class BlockObfuscation extends PixelObfuscation {
    private final int xBlockCount;
	private final int yBlockCount;

	public BlockObfuscation(ImageData image, String key) {
		this(image.getPixels(), image.getWidth(), image.getHeight(), key);
	}

	public BlockObfuscation(int[] pixels, int width, int height, String key) {
		this(pixels, width, height, key, 32, 32);
	}

	public BlockObfuscation(int[] pixels, int width, int height, String key, int xBlockCount, int yBlockCount) {
		super(pixels, width, height, key);
		this.xBlockCount = xBlockCount;
		this.yBlockCount = yBlockCount;
	}

	@Override
	public ImageData process(ProcessType processType) {
     		final int[] xArray = shuffle(xBlockCount);
		final int[] yArray = shuffle(yBlockCount);

		final int newWidth;
		final int newHeight;
		if (width % xBlockCount > 0) {
			newWidth = width + xBlockCount - width % xBlockCount;
		} else {
			newWidth = width;
		}
		if (height % yBlockCount > 0) {
			newHeight = height + yBlockCount - height % yBlockCount;
		} else {
			newHeight = height;
		}
		final int blockWidth = newWidth / xBlockCount;
		final int blockHeight = newHeight / yBlockCount;
		final int[] newPixels = new int[newWidth * newHeight];

		final int coreCount = Runtime.getRuntime().availableProcessors();
		final int taskCount = Math.min(newWidth, coreCount);
		final int step = (int) Math.ceil((double)newWidth / taskCount);

		List<Callable<Integer>> tasks = new ArrayList<>();

		for (int k = 0; k < taskCount; ++k) {
			final int begin = k * step;
			final int end = Math.min(begin + step, newWidth);

			Callable<Integer> task;
			if (processType == ProcessType.ENCRYPT) {
				task = () -> {
					for (int i = begin; i < end; ++i) {
						for (int j = 0; j < newHeight; ++j) {
							int n = j;
							int m = (xArray[(n / blockHeight) % xBlockCount] * blockWidth + i) % newWidth;
							m = xArray[m / blockWidth] * blockWidth + m % blockWidth;
							n = (yArray[m / blockWidth % yBlockCount] * blockHeight + n) % newHeight;
							n = yArray[n / blockHeight] * blockHeight + n % blockHeight;
							newPixels[i + j * newWidth] = pixels[m % width + n % height * width];
						}
					}
					return null;
				};

			} else {
				task = () -> {
					for (int i = begin; i < end; ++i) {
						for (int j = 0; j < newHeight; ++j) {
							int n = j;
							int m = (xArray[(n / blockHeight) % xBlockCount] * blockWidth + i) % newWidth;
							m = xArray[m / blockWidth] * blockWidth + m % blockWidth;
							n = (yArray[m / blockWidth % yBlockCount] * blockHeight + n) % newHeight;
							n = yArray[n / blockHeight] * blockHeight + n % blockHeight;
							newPixels[m + n * newWidth] = pixels[i % width + j % height * width];
						}
					}
					return null;
				};
			}

			tasks.add(task);
		}

		ExecutorService executorService = Executors.newFixedThreadPool(taskCount);
		try {
			executorService.invokeAll(tasks);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			executorService.shutdown();
		}

		return new ImageData(newPixels, newWidth, newHeight);
	}

}
