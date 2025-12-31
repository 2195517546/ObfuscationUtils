# ObfuscationUtils - 图片混淆工具库

一个简单的图片混淆加密工具库，提供多种图片加密混淆算法，可以轻松集成到你的 Java 项目中。

## 功能特性

- 🔐 **多种混淆算法**：支持 Tomato、Block、RowPixel、PerPixel、PicEncryptRow、PicEncryptRowColumn、Sort、Random 等多种混淆算法
- 🚀 **高性能**：利用多线程并行处理，支持大尺寸图片
- ⚡ **异步操作支持**：完整的异步 API，基于 CompletableFuture，适配高并发场景
- 🔄 **可逆加密**：所有算法支持加密和解密，使用正确的密钥可以完美还原
- 📦 **易于集成**：提供简单的 API，一行代码即可完成混淆/解混淆
- 🎨 **支持多种格式**：支持 PNG、JPG、GIF 等常见图片格式
- 🌐 **框架友好**：完美适配 Spring Boot、WebFlux 等异步框架

## 在项目中引入
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.2195517546</groupId>
    <artifactId>ObfuscationUtils</artifactId>
    <version>1.1.0</version>
</dependency>
```

## API 文档

- 项目工具类均在com.uiloalxise.*下

### ImageData 类

主要的图片数据封装类。

**构造方法：**
- `ImageData(int[] pixels, int width, int height)` - 从像素数组创建
- `ImageData(int[] pixels, int width, int height, String format)` - 指定格式

**静态方法：**
- `static ImageData fromFile(String filePath)` - 从文件加载
- `static ImageData fromInputStream(InputStream is)` - 从输入流加载
- `static ImageData fromInputStream(InputStream is, String format)` - 指定格式
- `static ImageData fromBytes(byte[] bytes)` - 从字节数组加载
- `static ImageData fromBytes(byte[] bytes, String format)` - 指定格式
- `static ImageData fromMultipartFile(MultipartFile file)` - 从 Spring Boot MultipartFile 加载
- `static ImageData fromBufferedImage(BufferedImage image)` - 从 BufferedImage 加载
- `static ImageData fromBufferedImage(BufferedImage image, String format)` - 指定格式

**实例方法：**
- `void saveToFile(String filePath)` - 保存到文件
- `int[] getPixels()` - 获取像素数组
- `int getWidth()` - 获取宽度
- `int getHeight()` - 获取高度
- `String getFormat()` - 获取格式
- `byte[] toBytes()` - 转换为字节数组
- `byte[] toBytes(String format)` - 指定格式转换为字节数组
- `InputStream toInputStream()` - 转换为输入流
- `InputStream toInputStream(String format)` - 指定格式转换为输入流
- `BufferedImage toBufferedImage()` - 转换为 BufferedImage
- `BufferedImage toBufferedImage(String format)` - 指定格式转换为 BufferedImage

### Util 工具类

提供快捷的加密/解密方法，所有方法都是静态方法。

**方法列表：**
- `tomatoEncrypt(String key, ImageData imageData)` / `tomatoDecrypt(...)`
- `blockEncrypt(String key, ImageData imageData)` / `blockDecrypt(...)`
- `rowPixelEncrypt(String key, ImageData imageData)` / `rowPixelDecrypt(...)`
- `perPixelEncrypt(String key, ImageData imageData)` / `perPixelDecrypt(...)`
- `picEncryptRowEncrypt(String key, ImageData imageData)` / `picEncryptRowDecrypt(...)`
- `picEncryptRowColumnEncrypt(String key, ImageData imageData)` / `picEncryptRowColumnDecrypt(...)`


## 构建 JAR 包

### 1. 构建普通 JAR 包

```bash
# Windows
.\gradlew clean build

# Linux/Mac
./gradlew clean build
```

构建完成后，jar 包位于：`build/libs/ObfuscationUtils-${version}.jar`

### 2. 构建包含源码和文档的完整包

```bash
# Windows
.\gradlew clean build sourcesJar javadocJar

# Linux/Mac
./gradlew clean build sourcesJar javadocJar
```

这将生成三个 jar 包：
- `ObfuscationUtils-${version}.jar` - 主 jar 包
- `ObfuscationUtils-${version}-sources.jar` - 源码包
- `ObfuscationUtils-${version}-javadoc.jar` - 文档包

### 3. 发布到本地 Maven 仓库

```bash
# Windows
.\gradlew publishToMavenLocal

# Linux/Mac
./gradlew publishToMavenLocal
```

## 在其他项目中使用

### 方式一：直接引入 JAR 包

1. 将 `ObfuscationUtils-${version}.jar` 复制到你的项目中
2. 在 IDE 中添加为库依赖

**Gradle 项目：**
```kotlin
dependencies {
    implementation(files("libs/ObfuscationUtils-${version}.jar"))
}
```

**Maven 项目：**
```xml
<dependency>
    <groupId>com.uiloalxise</groupId>
    <artifactId>ObfuscationUtils</artifactId>
    <version>${version}</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/libs/ObfuscationUtils-${version}.jar</systemPath>
</dependency>
```

### 方式二：通过 Maven Local 仓库

如果你已经执行了 `publishToMavenLocal`：

**Gradle 项目：**
```kotlin
repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("com.uiloalxise:ObfuscationUtils:${version}")
}
```

**Maven 项目：**
```xml
<dependency>
    <groupId>com.uiloalxise</groupId>
    <artifactId>ObfuscationUtils</artifactId>
    <version>${version}</version>
</dependency>
```

## 使用示例

### 基础示例

```java
import com.uiloalxise.ImageData;
import com.uiloalxise.Util;

public class Example {
    public static void main(String[] args) throws Exception {
        // 加载图片
        ImageData originalImage = ImageData.fromFile("path/to/image.jpg");
        
        // Tomato 混淆加密
        ImageData encrypted = Util.tomatoEncrypt("114514", originalImage);
        encrypted.saveToFile("encrypted.jpg");
        
        // 解密
        ImageData decrypted = Util.tomatoDecrypt("114514", encrypted);
        decrypted.saveToFile("decrypted.jpg");
    }
}
```

### 支持的混淆算法

#### 1. Tomato 混淆
```java
// 加密
ImageData encrypted = Util.tomatoEncrypt("密钥数字", imageData);
// 解密
ImageData decrypted = Util.tomatoDecrypt("密钥数字", encrypted);
```

#### 2. Block 混淆
```java
// 加密
ImageData encrypted = Util.blockEncrypt("任意字符串密钥", imageData);
// 解密
ImageData decrypted = Util.blockDecrypt("任意字符串密钥", encrypted);
```

#### 3. RowPixel 混淆（行像素混淆）
```java
// 加密
ImageData encrypted = Util.rowPixelEncrypt("密钥", imageData);
// 解密
ImageData decrypted = Util.rowPixelDecrypt("密钥", encrypted);
```

#### 4. PerPixel 混淆（像素级混淆）
```java
// 加密
ImageData encrypted = Util.perPixelEncrypt("密钥", imageData);
// 解密
ImageData decrypted = Util.perPixelDecrypt("密钥", encrypted);
```

#### 5. PicEncryptRow 混淆
```java
// 加密（支持小数密钥）
ImageData encrypted = Util.picEncryptRowEncrypt("0.618", imageData);
// 解密
ImageData decrypted = Util.picEncryptRowDecrypt("0.618", encrypted);
```

#### 6. PicEncryptRowColumn 混淆（行列混淆）
```java
// 加密（支持小数密钥）
ImageData encrypted = Util.picEncryptRowColumnEncrypt("0.618", imageData);
// 解密
ImageData decrypted = Util.picEncryptRowColumnDecrypt("0.618", encrypted);
```

#### 7. Sort 混淆（排序混淆）
```java
// 加密（无需密钥）
ImageData encrypted = Util.sortEncrypt(imageData);
// 解密
ImageData decrypted = Util.sortDecrypt(encrypted);
```

#### 8. Random 混淆（随机混淆）
```java
// 使用种子加密
ImageData encrypted = Util.randomEncrypt("114514", imageData);
// 使用相同种子解密
ImageData decrypted = Util.randomDecrypt("114514", encrypted);
```

### 异步 API 使用

从 v1.2 开始，ObfuscationUtils 提供了完整的异步操作支持，适用于高并发场景和响应式编程。

#### 异步加载和保存图片

```java
import com.uiloalxise.async.AsyncImageData;
import com.uiloalxise.async.AsyncUtil;

// 异步加载图片
CompletableFuture<ImageData> future = AsyncImageData.fromFileAsync("input.png");

// 异步保存图片
CompletableFuture<Void> saveFuture = AsyncImageData.saveToFileAsync(imageData, "output.png");

// 链式异步操作
AsyncImageData.fromFileAsync("input.png")
    .thenCompose(img -> AsyncUtil.tomatoEncryptAsync("0.618", img))
    .thenCompose(encrypted -> AsyncImageData.saveToFileAsync(encrypted, "encrypted.png"))
    .thenRun(() -> System.out.println("完成！"))
    .exceptionally(ex -> {
        ex.printStackTrace();
        return null;
    });
```

#### 异步混淆操作

```java
// 异步 Tomato 加密
CompletableFuture<ImageData> encrypted = AsyncUtil.tomatoEncryptAsync("0.618", imageData);

// 异步解密
CompletableFuture<ImageData> decrypted = AsyncUtil.tomatoDecryptAsync("0.618", encrypted.get());

// 对象级异步 API
TomatoObfuscation obfuscation = new TomatoObfuscation(imageData, 0.618);
CompletableFuture<ImageData> future = obfuscation.encryptAsync();
```

#### Spring Boot 异步集成示例

```java
@RestController
@RequestMapping("/api/image")
public class ImageController {
    
    @PostMapping("/encrypt")
    public CompletableFuture<ResponseEntity<byte[]>> encryptImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("key") String key) {
        
        return AsyncImageData.fromMultipartFileAsync(file)
            .thenCompose(img -> AsyncUtil.tomatoEncryptAsync(key, img))
            .thenCompose(encrypted -> AsyncImageData.toBytesAsync(encrypted))
            .thenApply(bytes -> ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(bytes))
            .exceptionally(ex -> ResponseEntity.status(500).build());
    }
}
```

### ImageData 支持多种输入方式

```java
// 从文件路径加载
ImageData image1 = ImageData.fromFile("path/to/image.jpg");
ImageData image2 = ImageData.fromFile("path/to/image.png");

// 从 Spring Boot MultipartFile 加载（Controller 接口）
ImageData image3 = ImageData.fromMultipartFile(file);

// 从 InputStream 加载
ImageData image4 = ImageData.fromInputStream(inputStream, "jpg");

// 从字节数组加载
ImageData image5 = ImageData.fromBytes(bytes, "png");

// 从像素数组创建
int[] pixels = ...; // ARGB 格式的像素数组
ImageData image6 = new ImageData(pixels, width, height);
ImageData image7 = new ImageData(pixels, width, height, "jpg");

// 保存到文件
image1.saveToFile("output.jpg");
image2.saveToFile("output.png");

// 转换为字节数组
byte[] bytes = image1.toBytes();

// 转换为 InputStream
InputStream is = image1.toInputStream();
```

## 注意事项

1. **密钥管理**：请妥善保管加密密钥，丢失密钥将无法解密图片
2. **图片格式**：
   - PNG 支持透明度，推荐用于需要保留透明度的场景
   - JPG 不支持透明度但文件更小，适合普通照片
3. **性能**：大尺寸图片处理可能需要较多内存，建议图片尺寸控制在 4K 以内
4. **密钥格式**：
   - Tomato 混淆需要数字密钥
   - Block、RowPixel、PerPixel 支持任意字符串
   - PicEncryptRow 和 PicEncryptRowColumn 只支持浮点数密钥(输入字符串需能转换为浮点数，快速工具默认是0.114514)
5. **异步操作**：
   - 异步操作适用于高并发场景和批量处理
   - 记得在应用关闭时调用 `ObfuscationExecutor.shutdown()` 关闭线程池
   - 支持自定义线程池，提供更灵活的资源管理

## 许可证

MIT License

## 作者

Uiloalxise

## 项目地址

GitHub: https://github.com/2195517546/ObfuscationUtils
