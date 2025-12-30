# ObfuscationUtils - 图片混淆工具库

一个简单的图片混淆加密工具库，提供多种图片加密混淆算法，可以轻松集成到你的 Java 项目中。

## 功能特性

- 🔐 **多种混淆算法**：支持 Tomato、Block、RowPixel、PerPixel、PicEncryptRow、PicEncryptRowColumn 等多种混淆算法
- 🚀 **高性能**：利用多线程并行处理，支持大尺寸图片
- 🔄 **可逆加密**：所有算法支持加密和解密，使用正确的密钥可以完美还原
- 📦 **易于集成**：提供简单的 API，一行代码即可完成混淆/解混淆
- 🎨 **支持多种格式**：支持 PNG、JPG、GIF 等常见图片格式

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

### ImageData 支持多种输入方式

```java
// 从文件路径加载
ImageData image1 = ImageData.fromFile("path/to/image.jpg");
ImageData image2 = ImageData.fromFile("path/to/image.png");

// 从像素数组创建
int[] pixels = ...; // ARGB 格式的像素数组
ImageData image3 = new ImageData(pixels, width, height);
ImageData image4 = new ImageData(pixels, width, height, "jpg");

// 保存到文件
image1.saveToFile("output.jpg");
image2.saveToFile("output.png");
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

## 许可证

MIT License

## 作者

Uiloalxise

## 项目地址

GitHub: https://github.com/2195517546/ObfuscationUtils
