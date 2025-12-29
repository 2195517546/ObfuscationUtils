package com.uiloalxise.utils.base;

import com.uiloalxise.ImageData;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Uiloalxise
 * @ClassName PixelObfuscation
 * @Description TODO
 */
public abstract class PixelObfuscation extends ImageObfuscation {
    public String key;

	public PixelObfuscation(ImageData image, String key) {
		super(image);
		this.key = key;
	}

	public PixelObfuscation(int[] pixels, int width, int height, String key) {
		super(pixels, width, height);
		this.key = key;
	}

	protected int[] shuffle(int length) {
		int[] arr = new int[length];
		for (int i = 0; i < length; ++i) {
			arr[i] = i;
		}
		for (int i = length - 1; i > 0; --i) {
			try {
				byte[] md5 = MessageDigest.getInstance("MD5").digest((key + i).getBytes());
				String hex = new BigInteger(1, md5).toString(16);
				if (hex.length() < 32) {
					for (int j = 32 - hex.length(); j > 0; --j) {
						hex = "0" + hex;
					}
				}
				int rand = Integer.parseInt(hex.substring(0, 7), 16) % (i + 1);
				int tmp = arr[rand];
				arr[rand] = arr[i];
				arr[i] = tmp;
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		return arr;
	}

	@Override
	public ImageData encrypt() {
		return process(ProcessType.ENCRYPT);
	}

	@Override
	public ImageData decrypt() {
		return process(ProcessType.DECRYPT);
	}
}
