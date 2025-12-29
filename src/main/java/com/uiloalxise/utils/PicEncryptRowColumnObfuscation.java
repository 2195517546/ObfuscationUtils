package com.uiloalxise.utils;

import com.uiloalxise.ImageData;
import com.uiloalxise.utils.base.PicEncryptObfuscation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Uiloalxise
 * @ClassName PicEncryptRowColumnObfuscation
 * @Description TODO
 */
public class PicEncryptRowColumnObfuscation extends PicEncryptObfuscation {
    private static final int maxTaskCount = 50;

	public PicEncryptRowColumnObfuscation(ImageData image, double key) {
		super(image, key);
	}

	public PicEncryptRowColumnObfuscation(int[] pixels, int width, int height, double key) {
		super(pixels, width, height, key);
	}

	@Override
	public ImageData process(ProcessType processType) {
		return processType == ProcessType.ENCRYPT ? encrypt() : decrypt();
	}

	@Override
	public ImageData encrypt() {
		// 第一步：行混淆
		int[] tempPixels = pixels.clone();
		int[] resultPixels = new int[pixels.length];
		double x = key;

		final int coreCount = Runtime.getRuntime().availableProcessors();
		ExecutorService executorService = Executors.newFixedThreadPool(coreCount);
		List<Callable<Integer>> tasks = new ArrayList<>();

		// 对每一行进行混淆
		for (int j = 0, offset = 0; j < height; ++j, offset += width) {
			final double[][] logisticMap = generateLogistic(x, width);
			x = logisticMap[width - 1][0];

			final int offset2 = offset;
            int[] finalTempPixels1 = tempPixels;
            tasks.add(() -> {
				final int[] positions = getSortedPositions(logisticMap, width);
				for (int i = 0; i < width; ++i) {
					resultPixels[i + offset2] = finalTempPixels1[positions[i] + offset2];
				}
				return null;
			});
			if (tasks.size() >= maxTaskCount) {
				try {
					executorService.invokeAll(tasks);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				tasks.clear();
			}
		}

		try {
			executorService.invokeAll(tasks);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		tasks.clear();

		// 第二步：列混淆（使用行混淆的结果）
		tempPixels = resultPixels.clone();
		x = key;

		for (int i = 0; i < width; ++i) {
			final double[][] logisticMap = generateLogistic(x, height);
			x = logisticMap[height - 1][0];

			final int i2 = i;
            int[] finalTempPixels = tempPixels;
            tasks.add(() -> {
				final int[] positions = getSortedPositions(logisticMap, height);
				for (int j = 0; j < height; ++j) {
					resultPixels[i2 + j * width] = finalTempPixels[i2 + positions[j] * width];
				}
				return null;
			});
			if (tasks.size() >= maxTaskCount) {
				try {
					executorService.invokeAll(tasks);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				tasks.clear();
			}
		}
		try {
			executorService.invokeAll(tasks);
			executorService.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return new ImageData(resultPixels, width, height);
	}

	@Override
	public ImageData decrypt() {
		// 解密顺序与加密相反：先列解密，再行解密
		// 第一步：列解密
		int[] tempPixels = pixels.clone();
		int[] resultPixels = new int[pixels.length];
		double x = key;

		final int coreCount = Runtime.getRuntime().availableProcessors();
		ExecutorService executorService = Executors.newFixedThreadPool(coreCount);
		List<Callable<Integer>> tasks = new ArrayList<>();

		for (int i = 0; i < width; ++i) {
			final double[][] logisticMap = generateLogistic(x, height);
			x = logisticMap[height - 1][0];

			final int i2 = i;
            int[] finalTempPixels = tempPixels;
            tasks.add(() -> {
				final int[] positions = getSortedPositions(logisticMap, height);
				for (int j = 0; j < height; ++j) {
					resultPixels[i2 + positions[j] * width] = finalTempPixels[i2 + j * width];
				}
				return null;
			});
			if (tasks.size() >= maxTaskCount) {
				try {
					executorService.invokeAll(tasks);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				tasks.clear();
			}
		}

		try {
			executorService.invokeAll(tasks);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		tasks.clear();

		// 第二步：行解密（使用列解密的结果）
		tempPixels = resultPixels.clone();
		x = key;

		for (int j = 0, offset = 0; j < height; ++j, offset += width) {
			final double[][] logisticMap = generateLogistic(x, width);
			x = logisticMap[width - 1][0];

			final int offset2 = offset;
            int[] finalTempPixels1 = tempPixels;
            tasks.add(() -> {
				final int[] positions = getSortedPositions(logisticMap, width);
				for (int i = 0; i < width; ++i) {
					resultPixels[positions[i] + offset2] = finalTempPixels1[i + offset2];
				}
				return null;
			});
			if (tasks.size() >= maxTaskCount) {
				try {
					executorService.invokeAll(tasks);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				tasks.clear();
			}
		}

		try {
			executorService.invokeAll(tasks);
			executorService.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return new ImageData(resultPixels, width, height);
	}
}
