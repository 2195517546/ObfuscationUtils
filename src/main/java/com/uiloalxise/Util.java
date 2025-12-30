package com.uiloalxise;

import com.uiloalxise.utils.*;

/**
 * @author Uiloalxise
 * @ClassName Util
 * @Description
 * 快速使用一个混淆工具
 * ENCRYPT,加密
 * DECRYPT,解密
 */
public class Util {

    public static ImageData tomatoEncrypt(String key,ImageData imageData) {
        TomatoObfuscation tomatoObfuscation = new TomatoObfuscation(imageData, Double.parseDouble(key));
        return tomatoObfuscation.encrypt();
    }

    public static ImageData tomatoDecrypt(String key,ImageData imageData) {
        TomatoObfuscation tomatoObfuscation = new TomatoObfuscation(imageData, Double.parseDouble(key));
        return tomatoObfuscation.decrypt();
    }

    public static ImageData blockEncrypt(String key,ImageData imageData) {
        BlockObfuscation blockObfuscation = new BlockObfuscation(imageData, key);
        return blockObfuscation.encrypt();
    }

    public static ImageData blockDecrypt(String key,ImageData imageData) {
        BlockObfuscation blockObfuscation = new BlockObfuscation(imageData, key);
        return blockObfuscation.decrypt();
    }

    /**
     * 行像素加密
     * @param key
     * @param imageData
     * @return
     */
    public static ImageData rowPixelEncrypt(String key,ImageData imageData) {
        RowPixelObfuscation rowPixelObfuscation = new RowPixelObfuscation(imageData, key);
        return rowPixelObfuscation.encrypt();
    }

    public static ImageData rowPixelDecrypt(String key,ImageData imageData) {
        RowPixelObfuscation rowPixelObfuscation = new RowPixelObfuscation(imageData, key);
        return rowPixelObfuscation.decrypt();
    }

    public static ImageData perPixelEncrypt(String key,ImageData imageData) {
        PerPixelObfuscation perPixelObfuscation = new PerPixelObfuscation(imageData, key);
        return perPixelObfuscation.encrypt();
    }

    public static ImageData perPixelDecrypt(String key,ImageData imageData) {
        PerPixelObfuscation perPixelObfuscation = new PerPixelObfuscation(imageData, key);
        return perPixelObfuscation.decrypt();
    }

    public static ImageData picEncryptRowEncrypt(String key, ImageData imageData) {
        try {
            double keyDouble = Double.parseDouble(key);
            PicEncryptRowObfuscation picEncryptRowObfuscation = new PicEncryptRowObfuscation(imageData,keyDouble);
            return picEncryptRowObfuscation.encrypt();
        }catch (Exception e)
        {
            PicEncryptRowObfuscation picEncryptRowObfuscation = new PicEncryptRowObfuscation(imageData,0.114514);
            return picEncryptRowObfuscation.encrypt();
        }
    }

    public static ImageData picEncryptRowDecrypt(String key, ImageData imageData) {
        try {
            double keyDouble = Double.parseDouble(key);
            PicEncryptRowObfuscation picEncryptRowObfuscation = new PicEncryptRowObfuscation(imageData,keyDouble);
            return picEncryptRowObfuscation.decrypt();
        }catch (Exception e)
        {
            PicEncryptRowObfuscation picEncryptRowObfuscation = new PicEncryptRowObfuscation(imageData,0.114514);
            return picEncryptRowObfuscation.decrypt();
        }
    }

    public static ImageData picEncryptRowColumnEncrypt(String key, ImageData imageData) {
        try {
            double keyDouble = Double.parseDouble(key);
            PicEncryptRowColumnObfuscation picEncryptRowColumnObfuscation = new PicEncryptRowColumnObfuscation(imageData,keyDouble);
            return picEncryptRowColumnObfuscation.encrypt();
        }catch (Exception e)
        {
            PicEncryptRowColumnObfuscation picEncryptRowColumnObfuscation = new PicEncryptRowColumnObfuscation(imageData,0.1145);
            return picEncryptRowColumnObfuscation.encrypt();
        }
    }

    public static ImageData picEncryptRowColumnDecrypt(String key, ImageData imageData) {
        try {
            double keyDouble = Double.parseDouble(key);
            PicEncryptRowColumnObfuscation picEncryptRowColumnObfuscation = new PicEncryptRowColumnObfuscation(imageData,keyDouble);
            return picEncryptRowColumnObfuscation.decrypt();
        }catch (Exception e)
        {
            PicEncryptRowColumnObfuscation picEncryptRowColumnObfuscation = new PicEncryptRowColumnObfuscation(imageData,0.1145);
            return picEncryptRowColumnObfuscation.decrypt();
        }
    }

    /**
     * 排序混淆加密
     * @param imageData 图片数据
     * @return 加密后的图片数据
     */
    public static ImageData sortEncrypt(ImageData imageData) {
        SortObfuscation sortObfuscation = new SortObfuscation(imageData);
        return sortObfuscation.encrypt();
    }

    /**
     * 排序混淆解密
     * @param imageData 图片数据
     * @return 解密后的图片数据
     */
    public static ImageData sortDecrypt(ImageData imageData) {
        SortObfuscation sortObfuscation = new SortObfuscation(imageData);
        return sortObfuscation.decrypt();
    }

    /**
     * 随机混淆加密（使用种子）
     * @param seed 随机种子
     * @param imageData 图片数据
     * @return 加密后的图片数据
     */
    public static ImageData randomEncrypt(String seed, ImageData imageData) {
        try {
            long seedLong = Long.parseLong(seed);
            RandomObfuscation randomObfuscation = new RandomObfuscation(imageData, seedLong);
            return randomObfuscation.encrypt();
        } catch (Exception e) {
            // 如果无法解析为long，使用seed的hashCode作为种子
            RandomObfuscation randomObfuscation = new RandomObfuscation(imageData, seed.hashCode());
            return randomObfuscation.encrypt();
        }
    }

    /**
     * 随机混淆解密（使用种子）
     * @param seed 随机种子
     * @param imageData 图片数据
     * @return 解密后的图片数据
     */
    public static ImageData randomDecrypt(String seed, ImageData imageData) {
        try {
            long seedLong = Long.parseLong(seed);
            RandomObfuscation randomObfuscation = new RandomObfuscation(imageData, seedLong);
            return randomObfuscation.decrypt();
        } catch (Exception e) {
            // 如果无法解析为long，使用seed的hashCode作为种子
            RandomObfuscation randomObfuscation = new RandomObfuscation(imageData, seed.hashCode());
            return randomObfuscation.decrypt();
        }
    }

    /**
     * 随机混淆加密（使用当前时间戳作为种子）
     * @param imageData 图片数据
     * @return 加密后的图片数据
     */
    public static ImageData randomEncrypt(ImageData imageData) {
        RandomObfuscation randomObfuscation = new RandomObfuscation(imageData);
        return randomObfuscation.encrypt();
    }

}
