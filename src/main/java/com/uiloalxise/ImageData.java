package com.uiloalxise;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author Uiloalxise
 * @ClassName ImageData
 * @Description 图片数据封装类
 */
public class ImageData {
    private final int[] pixels;
    private final int width;
    private final int height;
    private final String format;

    /**
     * 构造图片对象
     *
     * @param pixels 像素数组（ARGB格式）
     * @param width 图片宽度
     * @param height 图片高度
     */
    public ImageData(int[] pixels, int width, int height) {
        this(pixels, width, height, "png");
    }

    /**
     * 构造图片对象
     *
     * @param pixels 像素数组（ARGB格式）
     * @param width 图片宽度
     * @param height 图片高度
     * @param format 图片格式后缀（如 png, jpg）
     */
    public ImageData(int[] pixels, int width, int height, String format) {
        if (pixels == null || pixels.length != width * height) {
            throw new IllegalArgumentException("像素数组长度必须等于 width * height");
        }
        this.pixels = pixels;
        this.width = width;
        this.height = height;
        this.format = format != null ? format.toLowerCase() : "png";
    }

    /**
     * 获取像素数组
     */
    public int[] getPixels() {
        return pixels;
    }

    /**
     * 获取图片宽度
     */
    public int getWidth() {
        return width;
    }

    /**
     * 获取图片高度
     */
    public int getHeight() {
        return height;
    }

    /**
     * 获取像素总数
     */
    public int getPixelCount() {
        return width * height;
    }

    /**
     * 获取图片格式后缀
     */
    public String getFormat() {
        return format;
    }

    /**
     * 从文件加载图片
     *
     * @param filePath 文件路径
     * @return ImageData 对象
     * @throws IOException IO异常
     */
    public static ImageData fromFile(String filePath) throws IOException {
        File file = new File(filePath);
        BufferedImage image = ImageIO.read(file);
        if (image == null) {
            throw new IOException("无法读取图片文件: " + filePath);
        }
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);

        // 从文件路径提取格式
        String format = "png";
        int dotIndex = filePath.lastIndexOf('.');
        if (dotIndex > 0) {
            format = filePath.substring(dotIndex + 1).toLowerCase();
        }
        return new ImageData(pixels, width, height, format);
    }

    /**
     * 从 InputStream 加载图片
     *
     * @param inputStream 输入流
     * @return ImageData 对象
     * @throws IOException IO异常
     */
    public static ImageData fromInputStream(InputStream inputStream) throws IOException {
        return fromInputStream(inputStream, "png");
    }

    /**
     * 从 InputStream 加载图片，指定格式
     *
     * @param inputStream 输入流
     * @param format 图片格式（如 png, jpg）
     * @return ImageData 对象
     * @throws IOException IO异常
     */
    public static ImageData fromInputStream(InputStream inputStream, String format) throws IOException {
        BufferedImage image = ImageIO.read(inputStream);
        if (image == null) {
            throw new IOException("无法从输入流读取图片");
        }
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);

        return new ImageData(pixels, width, height, format != null ? format : "png");
    }

    /**
     * 从字节数组加载图片
     *
     * @param bytes 图片字节数组
     * @return ImageData 对象
     * @throws IOException IO异常
     */
    public static ImageData fromBytes(byte[] bytes) throws IOException {
        return fromBytes(bytes, "png");
    }

    /**
     * 从字节数组加载图片，指定格式
     *
     * @param bytes 图片字节数组
     * @param format 图片格式（如 png, jpg）
     * @return ImageData 对象
     * @throws IOException IO异常
     */
    public static ImageData fromBytes(byte[] bytes, String format) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        return fromInputStream(bais, format);
    }

    /**
     * 从 Spring Boot MultipartFile 加载图片
     *
     * @param multipartFile MultipartFile 对象（通常从 Controller 的 @RequestParam 获取）
     * @return ImageData 对象
     * @throws IOException IO异常
     */
    public static ImageData fromMultipartFile(Object multipartFile) throws IOException {
        try {
            // 使用反射调用 getInputStream() 和 getOriginalFilename() 方法
            InputStream inputStream = (InputStream) multipartFile.getClass()
                .getMethod("getInputStream")
                .invoke(multipartFile);

            String originalFilename = (String) multipartFile.getClass()
                .getMethod("getOriginalFilename")
                .invoke(multipartFile);

            // 从文件名提取格式
            String format = "png";
            if (originalFilename != null) {
                int dotIndex = originalFilename.lastIndexOf('.');
                if (dotIndex > 0) {
                    format = originalFilename.substring(dotIndex + 1).toLowerCase();
                }
            }

            return fromInputStream(inputStream, format);
        } catch (Exception e) {
            throw new IOException("无法从 MultipartFile 读取图片: " + e.getMessage(), e);
        }
    }

    /**
     * 从 BufferedImage 创建 ImageData
     *
     * @param bufferedImage BufferedImage 对象
     * @return ImageData 对象
     */
    public static ImageData fromBufferedImage(BufferedImage bufferedImage) {
        return fromBufferedImage(bufferedImage, "png");
    }

    /**
     * 从 BufferedImage 创建 ImageData，指定格式
     *
     * @param bufferedImage BufferedImage 对象
     * @param format 图片格式（如 png, jpg）
     * @return ImageData 对象
     */
    public static ImageData fromBufferedImage(BufferedImage bufferedImage, String format) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int[] pixels = new int[width * height];
        bufferedImage.getRGB(0, 0, width, height, pixels, 0, width);
        return new ImageData(pixels, width, height, format != null ? format : "png");
    }

    /**
     * 转换为字节数组
     *
     * @return 图片字节数组
     * @throws IOException IO异常
     */
    public byte[] toBytes() throws IOException {
        return toBytes(this.format);
    }

    /**
     * 转换为字节数组，指定格式
     *
     * @param format 图片格式（如 png, jpg）
     * @return 图片字节数组
     * @throws IOException IO异常
     */
    public byte[] toBytes(String format) throws IOException {
        BufferedImage image = toBufferedImage();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        String outputFormat = format != null ? format : this.format;
        boolean success = ImageIO.write(image, outputFormat, baos);

        if (!success) {
            throw new IOException("无法将图片转换为字节数组，格式: " + outputFormat);
        }

        return baos.toByteArray();
    }

    /**
     * 转换为 BufferedImage
     *
     * @return BufferedImage 对象
     */
    public BufferedImage toBufferedImage() {
        // 根据格式选择合适的图片类型
        int imageType = BufferedImage.TYPE_INT_RGB;
        if ("png".equals(format) || "gif".equals(format)) {
            imageType = BufferedImage.TYPE_INT_ARGB;
        }

        BufferedImage image = new BufferedImage(width, height, imageType);
        image.setRGB(0, 0, width, height, pixels, 0, width);
        return image;
    }

    /**
     * 转换为 InputStream
     *
     * @return InputStream 对象
     * @throws IOException IO异常
     */
    public InputStream toInputStream() throws IOException {
        return toInputStream(this.format);
    }

    /**
     * 转换为 InputStream，指定格式
     *
     * @param format 图片格式（如 png, jpg）
     * @return InputStream 对象
     * @throws IOException IO异常
     */
    public InputStream toInputStream(String format) throws IOException {
        byte[] bytes = toBytes(format);
        return new ByteArrayInputStream(bytes);
    }

    /**
     * 保存图片到文件
     *
     * @param filePath 文件路径
     * @throws IOException IO异常
     */
    public void saveToFile(String filePath) throws IOException {
        // 从文件路径提取格式
        String format = "png"; // 默认使用 PNG 以避免有损压缩
        int dotIndex = filePath.lastIndexOf('.');
        if (dotIndex > 0) {
            format = filePath.substring(dotIndex + 1).toLowerCase();
        }

        // 根据格式选择合适的图片类型
        // JPG 不支持透明度，需要使用 TYPE_INT_RGB
        int imageType = BufferedImage.TYPE_INT_RGB;
        if ("png".equals(format) || "gif".equals(format)) {
            imageType = BufferedImage.TYPE_INT_ARGB;
        }

        BufferedImage image = new BufferedImage(width, height, imageType);
        image.setRGB(0, 0, width, height, pixels, 0, width);

        File file = new File(filePath);
        // 确保父目录存在
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            boolean created = parentDir.mkdirs();
            if (!created && !parentDir.exists()) {
                throw new IOException("无法创建目录: " + parentDir.getAbsolutePath());
            }
        }

        // 写入文件并检查是否成功
        boolean success = ImageIO.write(image, format, file);
        if (!success) {
            throw new IOException("无法写入图片文件，没有找到合适的 writer: " + filePath + " (格式: " + format + ")");
        }

        // 验证文件是否真的被创建
        if (!file.exists()) {
            throw new IOException("文件写入后不存在: " + file.getAbsolutePath());
        }
    }

    /**
     * 获取文件扩展名
     */
    public String getFileExtension(String filePath) {
        int dotIndex = filePath.lastIndexOf('.');
        if (dotIndex > 0) {
            return filePath.substring(dotIndex + 1).toLowerCase();
        }
        return "png";
    }
}
