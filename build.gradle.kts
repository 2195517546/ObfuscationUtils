plugins {
    id("java")
    id("java-library")
    id("maven-publish")
}

group = "com.uiloalxise"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    withSourcesJar()  // 生成源码 jar
    withJavadocJar()  // 生成 javadoc jar
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes(
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version,
            "Implementation-Vendor" to "Uiloalxise"
        )
    }
}

// 配置 Javadoc 编码
tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
    (options as StandardJavadocDocletOptions).addStringOption("Xdoclint:none", "-quiet")
}

// Maven 发布配置（用于发布到本地或远程 Maven 仓库）
publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            pom {
                name.set("ObfuscationUtils")
                description.set("图片混淆工具库，提供多种图片加密混淆算法")
                url.set("https://github.com/uiloalxise/ObfuscationUtils")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }

                developers {
                    developer {
                        id.set("uiloalxise")
                        name.set("Uiloalxise")
                        email.set("your-email@example.com")
                    }
                }
            }
        }
    }

    repositories {
        maven {
            // 发布到本地 Maven 仓库
            url = uri(layout.buildDirectory.dir("repo"))
        }
    }
}

